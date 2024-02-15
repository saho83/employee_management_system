import {useState, useEffect} from 'react';
import {Link} from 'react-router-dom';
import axios from 'axios';

interface Employee {
    id: string;
    firstName: string;
    lastName: string;
    emailId: string;
}

export default function ListEmployeeComponent() {
    const [employees, setEmployees] = useState<Employee[]>([]);

    useEffect(() => {
        getAllEmployees();
    }, []);

    const getAllEmployees = () => {
        axios.get<Employee[]>('/api/employees')
            .then(response => {
                setEmployees(response.data);
                console.log(response.data);
            })
            .catch(error => {
                console.log(error);
            });
    };

    const deleteEmployee = (employeeId: string) => {
        axios.delete(`/api/${employeeId}`)
            .then(() => {
                getAllEmployees();
            })
            .catch(error => {
                console.log(error);
            });
    };

    return (
        <div className="container">
            <h2 className="text-center">List Employees</h2>
            <div className="col-md-auto btn-lg text-end">
            <Link to="/add-employee" className="btn btn-primary mb-2">
                Add Employee
            </Link>
            </div>
            <table className="table table-bordered table-striped">
                <thead>
                <tr>
                    <th>Employee Id</th>
                    <th>Employee First Name</th>
                    <th>Employee Last Name</th>
                    <th>Employee Email Id</th>
                    <th>Actions</th>

                </tr>
                </thead>
                <tbody>
                {employees.map(employee => (
                    <tr key={employee.id}>
                        <td>{employee.id}</td>
                        <td>{employee.firstName}</td>
                        <td>{employee.lastName}</td>
                        <td>{employee.emailId}</td>
                        <td>
                            <Link className="btn btn-dark" to={`/edit-employee/${employee.id}`}>
                                Update
                            </Link>
                            <button
                                className="btn btn-danger"
                                onClick={() => deleteEmployee(employee.id)}
                                style={{marginLeft: '10px'}}
                            >
                                Delete
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};


