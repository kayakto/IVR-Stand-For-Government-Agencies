import React from 'react';
import { Link } from 'react-router-dom';
import { setStateItem } from '../scripts/localstorage';

const Post = (props) => {
  return props.childCount > 1 ? (
    <>
      <img src="src\img\testOffer.svg" alt="" />
      <div className="service-text">
        <h3 className="service-title">{props.data.textSimple}</h3>
      </div>
    </>
  ) : (
    <Link to={`/standart-list/${props.childId}`} state={{id: props.childId, name: props.data.textSimple}}>
      <img src="src\img\testOffer.svg" alt="" />
      <div className="service-text">
        <h3 className="service-title">{props.data.textSimple}</h3>
        {/* <p className="service-descr">{props.data.textRussian}</p> */}
      </div>
    </Link>
  );
};

export default Post;
