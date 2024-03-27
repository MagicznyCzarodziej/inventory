import axios from 'axios';

const client = axios.create({
  baseURL: "http://10.0.2.2:8080",
  withCredentials: true,
  headers: {
    Accept: "application/json",
    "Content-Type": "application/json",
  }
})

export {client}