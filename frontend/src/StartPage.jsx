import React from "react";
import { Link } from "react-router-dom";
import greetingVideo from "./img/gif.gif";

const StartPage = () => {
  return (
    <>
      <h1 className="greeting span-8">
        Здравствуйте! <br /> Данный сервис является информационным помощником
      </h1>
      <img className="greeting-video span-8" src={greetingVideo} alt="" />
      <div className="btn-area flex">
        <Link className="btn-reset span-4 btn-red" to="/choose">
          Начать
        </Link>
        <button className="btn-reset span-4 btn-red">Помощь</button>
      </div>
    </>
  );
};

export default StartPage;
