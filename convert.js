class Item {
  constructor(name, price, count, tax) {
    this.classname = this.constructor.name // this is for deserialize

    this._name = name
    this._price = price
    this._count = count
    this._tax = tax
  }

  get name() {
    return this._name
  }

  get price() {
    return this._price
  }

  get count() {
    return this._count
  }

  get tax() {
    return this._tax
  }

  accept(visitor) {
    return visitor.visit(this)
  }

  serialize() {
    return JSON.stringify(this)
  }
}

class Discount {
  constructor(name, price) {
    this.classname = this.constructor.name // this is for deserialize

    this._name = name
    this._price = price
  }

  get name() {
    return this._name
  }

  get price() {
    return this._price
  }

  accept(visitor) {
    return visitor.visit(this)
  }

  serialize() {
    return JSON.stringify(this)
  }
}

class Tip {
  constructor(name, price) {
    this.classname = this.constructor.name // this is for deserialize

    this._name = name
    this._price = price
  }
  get name() {
    return this._name
  }

  get price() {
    return this._price
  }

  accept(visitor) {
    return visitor.visit(this)
  }

  serialize() {
    return JSON.stringify(this)
  }
}

function ItemVisitor() {
  this.visit = (data) => {
    switch (data.constructor) {
      case Item:
        const { price, count } = data
        return price * count
      default:
        return 0
    }
  }
}

function DiscountVisitor() {
  this.visit = (data) => {
    switch (data.constructor) {
      case Discount:
        const { price } = data
        return price * 1 // make sure price must be number because of *1
      default:
        return 0
    }
  }
}

function TaxVisitor() {
  this.visit = (data) => {
    switch (data.constructor) {
      case Item:
        const { price, count, tax } = data
        return price * count * tax
      default:
        return 0
    }
  }
}

function TipVisitor() {
  this.visit = (data) => {
    switch (data.constructor) {
      case Tip:
        const { price } = data
        return price * 1 // make sure price must be number because of *1
      default:
        return 0
    }
  }
}
/**
 *
 * @param {Array} cart
 * @returns
 */
function calculateItemPrice(cart) {
  const visitor = new ItemVisitor()
  return cart.reduce((sum, item) => sum + item.accept(visitor), 0)
}

/**
 *
 * @param {Array} cart
 * @param {Number} sumOfItemPrice
 * @returns
 */
function calculateDiscount(cart, sumOfItemPrice) {
  const discountDetails = []
  const discountVisitor = new DiscountVisitor()

  const sumOfDiscountPrice = cart.reduce((sum, discount) => {
    const discountRate = discount.accept(discountVisitor)
    const discountedPrice = sum * discountRate

    const index = cart.findIndex((item) => item.price === discountRate)
    const foundItem = index > -1 ? cart[index] : null

    if (foundItem) {
      discountDetails.push({
        name: `Discount(${String(discountRate * 100).padStart(2)}%)`,
        price: discountedPrice,
      })
    }

    return sum - discountedPrice
  }, sumOfItemPrice)

  return {
    discount: sumOfItemPrice - sumOfDiscountPrice,
    discountDetails,
  }
}

function calculateTax(cart) {
  const discountVisitor = new DiscountVisitor()
  const discountsArray = []

  // add all discounts
  cart.forEach((item) => {
    if (item.accept(discountVisitor)) {
      discountsArray.push(item.accept(discountVisitor))
    }
  })
  const sum = []
  const taxs = []
  let index = 0
  cart.forEach((item) => {
    if (item.constructor == Item) {
      sum[index] = item.price * item.count
      discountsArray.forEach((discocount) => {
        sum[index] = sum[index] - sum[index] * discocount
      })
      sum[index] = sum[index] * item.tax
      taxs.push({
        name: `Tax(${String(item.tax * 100).padStart(2)}%)`,
        price: sum[index],
      })
      index = index + 1
    }
  })
  return {
    tax: sum.reduce((acc, val) => acc + val, 0),
    taxDetails: taxs,
  }
}

function calculateTip(cart, subtotal, tax) {
  const visitor = new TipVisitor()
  const sumOfTip = cart.reduce((sum, item) => sum + item.accept(visitor), 0)
  return (1 * subtotal + 1 * tax) * sumOfTip
}

function run() {
  const cart = []

  cart.push(new Item('item01', 1000, 1, 0.05)) // 5%
  cart.push(new Item('item02', 2000, 1, 0.1)) // 10%
  cart.push(new Item('item03', 3000, 1, 0.13)) // 13%
  cart.push(new Discount('Discount 5%', 0.05)) // 5%
  cart.push(new Discount('Discount 10%', 0.1)) // 10%
  cart.push(new Tip('tip 10%', 0.1)) // 10%

  const sumOfItemPrice = calculateItemPrice(cart)
  console.log('sumOfItemPrice = ', sumOfItemPrice)

  const { discount, discountDetails } = calculateDiscount(cart, sumOfItemPrice)
  console.log('discount = ', discount)
  console.log('discountDetails = ', discountDetails)

  const subtotal = sumOfItemPrice - discount
  console.log('subtotal = ', subtotal)

  const { tax, taxDetails } = calculateTax(cart)
  console.log('tax = ', tax)
  console.log('taxDetails = ', taxDetails)

  const tip = calculateTip(cart, subtotal, tax)
  console.log('tip = ', tip)

  const total = subtotal + tax + tip
  console.log('total = ', total)
}

run()

// function serialize(instance) {
//   var str = JSON.stringify(instance)
//   // save str or whatever
// }

// function unserialize(str, theClass) {
//   var instance = new theClass()
//   // NOTE: if your constructor checks for unpassed arguments,
//   // then just pass dummy ones to prevent throwing an error

//   var serializedObject = JSON.parse(str)
//   Object.assign(instance, serializedObject)
//   return instance
// }

// function deserialize(json) {
//   //o is [Object object], but it contains every state of the original object
//   let o = JSON.parse(json)

//   const original_class = isNode()
//     ? eval(o.classname) // in case of Node
//     : new Function(`return ${o.classname}`)() // in case of Web browser

//   return Object.assign(new original_class(), o)
// }

// function isNode() {
//   return typeof window == 'undefined' ? true : false
// }

// let foo = new Item('item01', 1000, 1, 0.05)
// let json = foo.serialize()
// console.log(deserialize(json))
