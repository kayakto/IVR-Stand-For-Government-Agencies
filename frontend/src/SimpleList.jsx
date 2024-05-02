import React, { useDeferredValue, useEffect, useRef } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import Post from './components/Post';
import BackArrowList from './components/BackArrowList';
import { useLocalObservable } from 'mobx-react-lite';
import {
  setStateItem,
  getStateItem,
  clearStorage,
} from './scripts/localstorage';

const SimpleList = () => {
  const [offers, setOffers] = useState([]);

  const tempChild1 = [
    {
      id: '2',
      parrent: '1',
      textSimple: 'Тут мало текста',
      textRussian: 'Внесение денег на карту фронтендеру на тинькофф',
      children: ['2', '2'],
    },
    {
      id: '3',
      parrent: '1',
      textSimple: 'А тут очень много',
      textRussian: 'Внесение денег на карту фронтендеру на сбер',
      children: ['333'],
    },
  ];

  const servicesHistory = useLocalObservable(() => ({
    history: [],

    addToHistory(newState) {
      this.history.push(newState);
    },

    getFromHistory() {
      return this.history.pop();
    },
  }));

  const goDeep = () => {
    servicesHistory.addToHistory(offers);
    setOffers(tempChild1);
  };

  const navigate = useNavigate();

  const test = () => {
    if (servicesHistory.history.length > 0) {
      // alert('yes')
      const prevState = servicesHistory.getFromHistory();
      setOffers(prevState);
    } else {
      navigate(-1);
    }
  };

  useEffect(() => {
    setOffers([
      {
        id: '1',
        parrent: '',
        textSimple: 'Внесение денег на карту фронтендеру',
        textRussian:
          'Lorem ipsum dolor sit amet. A ultrices ultrices pidoras nisleget.',
        children: ['2', '3'],
      },
      {
        id: '4',
        parrent: '',
        textSimple: 'Внесение денег на карту фронтендеру',
        textRussian:
          'Lorem ipsum dolor sit amet. A ultrices ultrices pidoras nisleget.',
        children: ['2', '3'],
      },
      {
        id: '5',
        parrent: '',
        textSimple: 'Внесение денег на карту фронтендеру',
        textRussian:
          'Lorem ipsum dolor sit amet. A ultrices ultrices pidoras nisleget.',
        children: ['2', '3'],
      },
      {
        id: '6',
        parrent: '',
        textSimple: 'Тут один ребенок',
        textRussian:
          'Lorem ipsum dolor sit amet. A ultrices ultrices pidoras nisleget.',
        children: ['3'],
      },
      {
        id: '7',
        parrent: '',
        textSimple: 'Тут тоже',
        textRussian:
          'Lorem ipsum dolor sit amet. A ultrices ultrices pidoras nisleget.',
        children: ['2'],
      },
    ]);
  }, []);

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
                onClick={() => goDeep()}
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
