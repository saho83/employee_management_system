
import './App.css';
import {Route, Routes} from "react-router-dom";
import ListEmployeeComponent from './components/ListEmployeeComponent';


export default function App() {






    return (
        <>
            <Routes>
                <Route path="/" element={<ListEmployeeComponent/>}/>
            </Routes>

        </>


    );




}