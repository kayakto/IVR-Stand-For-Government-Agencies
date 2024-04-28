import React from 'react'
import { Link } from 'react-router-dom'

const VideoPost = (props) => {
  return props.childCount > 1 ?
  (
          <li style={{backgroundImage: `url(${props.data.videoUrl})`, backgroundSize: "cover"}} className="service-ivr-item flex span-12 ">
              <div className="service-text">
                  <h3 className="service-title ivr-list-title">{props.data.textSimple}</h3>
                  <p className="service-descr ivr-list-descr">
                      {props.data.textRussian}
                  </p>
              </div>
          </li>
  ) : (
      <Link to={`/ivr-list/${props.childId}`} state={{id: props.childId}}>
          <li style={{backgroundImage: `url(${props.data.videoUrl})`, backgroundSize: "cover"}} className="service-ivr-item flex span-12">
              <div className="service-text">
                  <h3 className="service-title ivr-list-title">{props.data.textSimple}</h3>
                  <p className="service-descr ivr-list-descr">
                      {props.data.textRussian}
                  </p>
              </div>
          </li>
      </Link>
  )

}

export default VideoPost