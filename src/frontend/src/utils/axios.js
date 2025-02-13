import axios from "axios";

const instance = axios.create({
    baseURL: "http://localhost:8080/api", // backend API URL
    headers: {
        "Content-Type": "application/json",
    },
});

// Add token to requests
instance.interceptors.request.use((config) => {
    const token = localStorage.getItem("token");
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

export default instance;