import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import EmployeeService from '../services/EmployeeService';

interface Employee {
    id?: string;
    firstName: string;
    lastName: string;
    emailId: string;
}

export default function AddEmployeeComponent() {
    const [firstName, setFirstName] = useState<string>('');
    const [lastName, setLastName] = useState<string>('');
    const [emailId, setEmailId] = useState<string>('');
    const navigate = useNavigate();
    const { id } = useParams<{ id?: string }>();

    const saveOrUpdateEmployee = (e: React.FormEvent) => {
        e.preventDefault();

        const employee: Employee = { firstName, lastName, emailId };

        if (id) {
            EmployeeService.updateEmployee(id, employee)
                .then(() => {
                    navigate('/employees');
                })
                .catch((error: any) => {
                    console.log(error);
                });
        } else {
            EmployeeService.createEmployee(employee)
                .then((response: { data: any; }) => {
                    console.log(response.data);
                    navigate('/employees');
                })
                .catch((error: any) => {
                    console.log(error);
                });
        }
    };

    useEffect(() => {
        if (id) {
            EmployeeService.getEmployeeById(id)
                .then((response: { data: { firstName: string; lastName: string; emailId: string; }; }) => {
                    setFirstName(response.data.firstName);
                    setLastName(response.data.lastName);
                    setEmailId(response.data.emailId);
                })
                .catch((error: any) => {
                    console.log(error);
                });
        }
    }, [id]);

    const title = () => {
        if (id) {
            return <h2 className="text-center">Update Employee</h2>;
        } else {
            return <h2 className="text-center">Add Employee</h2>;
        }
    };

    return (
        <div>
            <br />
            <br />
            <div className="container">
                <div className="row">
                    <div className="card col-md-12 offset-md-1">
                        {title()}
                        <div className="card-body">
                            <form>
                                <div className="form-group mb-2">
                                    <label className="form-label">First Name :</label>
                                    <input
                                        type="text"
                                        placeholder="Enter first name"
                                        name="firstName"
                                        className="form-control"
                                        value={firstName}
                                        onChange={(e) => setFirstName(e.target.value)}
                                    />
                                </div>

                                <div className="form-group mb-2">
                                    <label className="form-label">Last Name :</label>
                                    <input
                                        type="text"
                                        placeholder="Enter last name"
                                        name="lastName"
                                        className="form-control"
                                        value={lastName}
                                        onChange={(e) => setLastName(e.target.value)}
                                    />
                                </div>

                                <div className="form-group mb-2">
                                    <label className="form-label">Email Id :</label>
                                    <input
                                        type="email"
                                        placeholder="Enter email Id"
                                        name="emailId"
                                        className="form-control"
                                        value={emailId}
                                        onChange={(e) => setEmailId(e.target.value)}
                                    />
                                </div>

                                <button className="btn btn-success" onClick={(e) => saveOrUpdateEmployee(e)}>
                                    Submit
                                </button>
                                <Link to="/employees" className="btn btn-danger">
                                    Cancel
                                </Link>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};



