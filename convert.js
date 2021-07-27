class Item {
  constructor(name, price, count, tax) {
    // this classname is for deserialize
    this.classname = this.constructor.name

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
    // this classname is for deserialize
    this.classname = this.constructor.name

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
    // this classname is for deserialize
    this.classname = this.constructor.name

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
  this.visit = (arg) => {
    switch (arg.constructor) {
      case Item:
        const item = arg
        return item.price * item.count
      default:
        return 0
    }
  }
}

function DiscountVisitor() {
  this.visit = (arg) => {
    switch (arg.constructor) {
      case Discount:
        const discount = arg
        return discount.price
      default:
        return 0
    }
  }
}

function TaxVisitor() {
  this.visit = (arg) => {
    switch (arg.constructor) {
      case Item:
        const item = arg
        return item.price * item.tax
      default:
        return 0
    }
  }
}

function TipVisitor() {
  this.visit = (arg) => {
    switch (arg.constructor) {
      case Tip:
        const tip = arg
        return tip.price
      default:
        return 0
    }
  }
}

function calculateItemPrice(cart) {
  const visitor = new ItemVisitor()
  return cart.reduce((sum, item) => sum + item.accept(visitor), 0).toFixed(2)
}

function calculateDiscount(cart, sumOfItemPrice) {
  const visitor = new DiscountVisitor()
  const discountPercent = cart.reduce(
    (sum, item) => sum + item.accept(visitor),
    0,
  )
  return (sumOfItemPrice * discountPercent).toFixed(2)
}

function calculateTax(cart) {
  let discount = new DiscountVisitor()
  let sumDiscount = cart.reduce((sum, item) => sum + item.accept(discount), 0)

  let tax = new TaxVisitor()
  let sumTax = cart.reduce((sum, item) => {
    const result = item.accept(tax)
    return sum + (sumDiscount ? result * (1.0 - sumDiscount) : result)
  }, 0)
  return sumTax.toFixed(2)
}

function calculateTip(cart, subtotal, tax) {
  const visitor = new TipVisitor()
  const sumOfTip = cart.reduce((sum, item) => sum + item.accept(visitor), 0)
  return ((1 * subtotal + 1 * tax) * sumOfTip).toFixed(2)
}

function run() {
  const cart = []

  cart.push(new Item('item01', 1000, 1, 0.05)) // 5%
  cart.push(new Item('item02', 2000, 1, 0.1)) // 10%
  cart.push(new Discount('discountPrice 10%', 0.1)) // 10%
  cart.push(new Tip('tip 10%', 0.1)) // 10%

  const sumOfItemPrice = calculateItemPrice(cart)
  console.log('sumOfItemPrice = ', sumOfItemPrice)

  const discountPrice = calculateDiscount(cart, sumOfItemPrice)
  console.log('discountedPrice = ', discountPrice)

  const subtotal = (sumOfItemPrice - discountPrice).toFixed(2)
  console.log('subtotal =  ', subtotal)

  const tax = calculateTax(cart, subtotal)
  console.log('tax = ', tax)

  const tip = calculateTip(cart, subtotal, tax)
  console.log('tip = ', tip)

  const total = (1 * subtotal + 1 * tax + 1 * tip).toFixed(2)
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

function deserialize(json) {
  //o is [Object object], but it contains every state of the original object
  let o = JSON.parse(json)

  const original_class = isNode()
    ? eval(o.classname) // in case of Node
    : new Function(`return ${o.classname}`)() // in case of Web browser

  return Object.assign(new original_class(), o)
}

function isNode() {
  return typeof window == 'undefined' ? true : false
}

let foo = new Item('item01', 1000, 1, 0.05)
let json = foo.serialize()
console.log(deserialize(json))
