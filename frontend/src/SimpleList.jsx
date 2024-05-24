import React, { useDeferredValue, useEffect, useRef } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import Post from './components/Post';
import BackArrowList from './components/BackArrowList';
import { useLocalObservable } from 'mobx-react-lite';
import axios from 'axios';
import SimpleSearch from './components/SimpleSearch';

const SimpleList = () => {
  const [offers, setOffers] = useState([]);
  const [title, setTitle] = useState('Услуги')

  const servicesHistory = useLocalObservable(() => ({
    history: [],

    addToHistory(newState) {
      this.history.push(newState);
    },

    getFromHistory() {
      return this.history.pop();
    },
  }));

  const titleHistory = useLocalObservable(() => ({
    history: [],

    addToHistory(newState) {
      this.history.push(newState);
    },

    getFromHistory() {
      return this.history.pop();
    },
  }));
  
  const goDeep = async(post) => {
    servicesHistory.addToHistory(offers);
    titleHistory.addToHistory(title)
    const address = post.children.join('&ids=')
    setTitle(post.textSimple)
    await axios.get(`https://pincode-dev.ru/ivr-hor/api/videoDoc?ids=${address}`)
    .then(res => res.data).then(data => setOffers(data))
    .catch(e => console.log(e))
  };

  const navigate = useNavigate();

  const test = () => {
    if (servicesHistory.history.length > 0) {
      const prevServiceState = servicesHistory.getFromHistory();
      const prevTitleState = titleHistory.getFromHistory()
      setOffers(prevServiceState);
      setTitle(prevTitleState)
    } else {
      navigate(-1);
    }
  };

  useEffect(() => {
    axios.get('https://pincode-dev.ru/ivr-hor/api/videoDoc/main')
   .then(res => res.data).then(data => setOffers(data))
   .catch(e => console.log(e))}, []);


  return (
    <>
      <div className="top-section flex">
        <div className="top-text flex">
          <BackArrowList back={() => test()} />
          <h2 className="title">{title}</h2>
          <h2 className="subtitle title">{offers.length} вариантов</h2>
        </div>
        <SimpleSearch offers={offers} title={title}
         servicesHistory={servicesHistory} titleHistory={titleHistory} setTitle={setTitle} setOffers={setOffers}/>
        <div className="btn-area flex">
          <button className="btn-reset span-2 btn-beige">Поиск</button>
          <button onClick={() => navigate('/choose')} className="btn-reset span-2 btn-brown">Язык</button>
        </div>
      </div>

      {offers.length ? (
        <div className="service-list flex">
          {offers.map((post) =>
            post.children.length > 1 ? (
              <div
                className="service-item flex"
                key={post.id}
                onClick={() => goDeep(post)}
              >
                <Post data={post} childCount={post.children.length} />
              </div>
            ) : (
                <Post key={post.id}
                  data={post}
                  childCount={post.children.length}
                  childId={post.children[0]}
                />
            )
          )}
        </div>
      ) : (
        <>
          <h2 className="service-title">Ничего не найдено</h2>
        </>
      )}
    </>
  );
};

export default SimpleList;
