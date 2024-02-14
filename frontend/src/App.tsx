
import './App.css';
import {Route, Routes} from "react-router-dom";
import ListEmployeeComponent from './components/ListEmployeeComponent';
import FooterComponent from "./components/FooterComponent.tsx";
import HeaderComponent from "./components/HeaderComponent.tsx";


export default function App() {






    return (
        <>
            <HeaderComponent />
            <div className="content-container">
            <Routes>
                <Route path="/" element={<ListEmployeeComponent/>}/>
                <Route path="/employees" element={<ListEmployeeComponent/>}/>
            </Routes>
            </div>
            <FooterComponent />

        </>


    );




}