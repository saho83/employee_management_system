import React from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import ListEmployeeComponent from "./components/ListEmployeeComponent";
import ReactDOM from "react-dom";

function App() {
    return (
        <Router>
            <div>
                <Route exact path="/" component={ListEmployeeComponent} />
            </div>
        </Router>
    );
}

ReactDOM.render(<App />, document.getElementById('root'));

export default App;
