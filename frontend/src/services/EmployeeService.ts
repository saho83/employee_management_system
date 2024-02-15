import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

class EmployeeService {
    getAllEmployees() {
        return axios.get(API_BASE_URL);
    }

    getEmployeeById(id: string) {
        return axios.get(`${API_BASE_URL}/${id}`);
    }

    createEmployee(employee: any) {
        return axios.post(API_BASE_URL, employee);
    }

    updateEmployee(id: string, employee: any) {
        return axios.put(`${API_BASE_URL}/${id}`, employee);
    }

    deleteEmployee(id: string) {
        return axios.delete(`${API_BASE_URL}/${id}`);
    }
}

export default new EmployeeService();
