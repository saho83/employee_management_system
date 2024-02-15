
import './App.css';
import {Route, Routes} from "react-router-dom";
import ListEmployeeComponent from './components/ListEmployeeComponent';
import FooterComponent from "./components/FooterComponent.tsx";
import HeaderComponent from "./components/HeaderComponent.tsx";
import AddEmployeeComponent from "./components/AddEmployeeComponent.tsx";


export default function App() {






    return (
        <>
            <HeaderComponent />
            <div className="content-container">
            <Routes>
                <Route path="/" element={<ListEmployeeComponent/>}/>
                <Route path="/employees" element={<ListEmployeeComponent/>}/>
                <Route path="/add-employee" element={<AddEmployeeComponent/>}/>
                <Route path="/edit-employee/:id" element={<AddEmployeeComponent/>}/>
            </Routes>
            </div>
            <FooterComponent />

        </>


    );




}