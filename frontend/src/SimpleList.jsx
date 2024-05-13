import React, { useDeferredValue, useEffect, useRef } from 'react';
import { Link, json, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import Post from './components/Post';
import BackArrowList from './components/BackArrowList';
import { useLocalObservable } from 'mobx-react-lite';
import axios from 'axios';
import {
  setStateItem,
  getStateItem,
  clearStorage,
} from './scripts/localstorage';

const SimpleList = () => {
  const [offers, setOffers] = useState([]);


  const servicesHistory = useLocalObservable(() => ({
    history: [],

    addToHistory(newState) {
      this.history.push(newState);
    },

    getFromHistory() {
      return this.history.pop();
    },
  }));

  const goDeep = (children) => {
    servicesHistory.addToHistory(offers);

    axios.get(`http://localhost:8080/api/videoDoc?ids=${children.join('ids=')}'`)
    .then(res => res.data).then(data => setOffers(data))
    .catch(e => console.log(e))
  };

  const navigate = useNavigate();

  const test = () => {
    if (servicesHistory.history.length > 0) {
      const prevState = servicesHistory.getFromHistory();
      setOffers(prevState);
    } else {
      navigate(-1);
    }
  };

  useEffect(() => {
   axios.get('http://localhost:8080/api/videoDoc/main')
   .then(res => res.data).then(data => setOffers(data))
   .catch(e => console.log(e))}, []);

  return (
    <>
      <div className="top-section flex">
        <div className="top-text flex">
          <BackArrowList back={() => test()} />
          <h2 className="title">Услуги</h2>
          <h2 className="subtitle title">{offers.length} вариантов</h2>
        </div>
        <div className="btn-area flex">
          <button className="btn-reset span-2 btn-beige">Поиск</button>
          <button className="btn-reset span-2 btn-red">Помощь</button>
        </div>
      </div>

      {offers.length ? (
        <ul className="service-list flex">
          {offers.map((post) =>
            post.children.length > 1 ? (
              <li
                className="service-item flex"
                key={post.id}
                onClick={() => goDeep(post.children)}
              >
                <Post data={post} childCount={post.children.length} />
              </li>
            ) : (
              <li className="service-item flex" key={post.id}>
                <Post
                  
                  data={post}
                  childCount={post.children.length}
                  childId={post.children[0]}
                />
              </li>
            )
          )}
        </ul>
      ) : (
        <>
          <h2 className="service-title">Ничего не найдено</h2>
        </>
      )}
    </>
  );
};

export default SimpleList;
