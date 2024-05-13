import React from 'react'

const VariantWord = (props) => {
  return (
    <div>
        <span>{props.text}</span>
        <button onClick={() => props.delete(props.id)}>X</button>
    </div>
  )
}

export default VariantWord