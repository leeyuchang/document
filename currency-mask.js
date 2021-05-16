import React, { useState } from 'react'

function App() {
  const [value, setValue] = useState('')

  const transform = (e) => {
    // if (value.length === 1) setValue((prev) => '0' + prev)
    setValue(
      parseFloat(
        e.target.value.replace(/[^\d]/g, '').replace(/(\d\d?)$/, '.$1'),
      ).toFixed(2),
    )
  }
  return (
    <div className="App">
      <input type="text" value={value} onChange={transform} placeholder="0.00" />
    </div>
  )
}

export default App
