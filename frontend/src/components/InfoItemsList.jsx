import React from 'react'
import { Link } from 'react-router-dom'

const InfoItemsList = (props) => {

  const infoChilds = props.info
  const id = props.id

  return (
    <div className="extra-info-wrap span-4 flex">

          {infoChilds.map(infoChild => (
            <Link key={infoChild.id} to={`/standart-list/${id}/info/${infoChild.id}`}>
<button className="btn-reset btn-red span-4">{infoChild.textSimple}</button>
            </Link>
          ))}
          

      </div>
  )
}

export default InfoItemsList