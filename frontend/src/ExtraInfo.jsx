import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import BackArrowList from './components/BackArrowList';
import { Link } from 'react-router-dom';

const ExtraInfo = () => {
  const location = useLocation();
  const infCh = location.state ? location.state.infoChild : [];
  const id = location.state ? location.state.id : '0';
  const navigate = useNavigate();
  // фетчить прикол

  const infoTemp = [
    {
      id: '1',
      parrent: '2',
      textSimple: 'Что такое паспорт?',
      textRussian: 'лорем ипсум)',
      children: [],
      videoUrl:
        'https://avatars.dzeninfra.ru/get-zen_doc/1718701/pub_5e297d28df944400bd8adbd0_5e297f0143fdc000af4d03ba/orig',
      infoChild: [],
    },
    {
      id: '4',
      parrent: '2',
      textSimple: 'Что такое снилс?',
      textRussian: 'лорем ипсум)',
      children: [],
      videoUrl:
        'https://avatars.dzeninfra.ru/get-zen_doc/1718701/pub_5e297d28df944400bd8adbd0_5e297f0143fdc000af4d03ba/orig',
      infoChild: [],
    },
    {
      id: '3',
      parrent: '2',
      textSimple: 'Что такое лорем ипсум?',
      textRussian: 'лорем ипсум)',
      children: [],
      videoUrl:
        'https://avatars.dzeninfra.ru/get-zen_doc/1718701/pub_5e297d28df944400bd8adbd0_5e297f0143fdc000af4d03ba/orig',
      infoChild: [],
    },
  ];
  return (
    <>
      <div className="top-section flex">
        <div className="top-text flex">
          <BackArrowList
            back={() => {
              navigate(-1);
            }}
          />
          <h2 className="title">Дополнительная информация</h2>
        </div>
      </div>
      <div className="extra-info-wrap span-4 flex">
        {infoTemp.map((info) => (
          <Link key={info.id} to={`/ivr-list/${id}.info/${info.id}`}>
            <button className="btn-reset btn-red span-4">
              {info.textSimple}
            </button>
          </Link>
        ))}
      </div>
    </>
  );
};

export default ExtraInfo;
