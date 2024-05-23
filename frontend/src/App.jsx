import React from 'react';
import StartPage from './StartPage';
import { Route, Routes } from 'react-router-dom';
import SimpleList from './SimpleList';
import ChooseLang from './ChooseLang';
import SimpleOfferPage from './SimpleOfferPage';
import VideoList from './VideoList';
import IvrServicePage from './IvrServicePage';
import ExtraInfo from './ExtraInfo';
import IVRSearchPage from './IVRSearchPage'
import InfoPage from './InfoPage';

const App = () => {
  return (
    <Routes>
      <Route path="/" element={<StartPage />}></Route>
      <Route path="/choose" element={<ChooseLang />}></Route>
      <Route path="/standart-list" element={<SimpleList />}></Route>
      <Route path="/standart-list/:id" element={<SimpleOfferPage />}></Route>
      <Route path="/standart-list/:id/info" element={<ExtraInfo />}></Route>
      <Route path="/ivr-list" element={<VideoList />}></Route>
      <Route path="/ivr-list/:id" element={<IvrServicePage />}></Route>
      <Route path="/ivr-list/:id/info" element={<ExtraInfo />}></Route>
      <Route path="/ivr-list/:id/info/:infid" element={<InfoPage/>}></Route>
      <Route path="/standart-list/:id/info/:infid" element={<InfoPage/>}></Route>
      <Route path="/ivr-list/search" element={<IVRSearchPage />}></Route>
    </Routes>
  );
};

export default App;
