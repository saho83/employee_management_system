
import './App.css';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import ListEmployeeComponent from './components/ListEmployeeComponent';


export default function App() {






    return (
        <>
            <Router>
                <div>
                    <Route path="/" Component={ListEmployeeComponent} />
                </div>
            </Router>





        </>
    );
}


