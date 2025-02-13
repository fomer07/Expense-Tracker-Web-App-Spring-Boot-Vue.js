<template>
  <div class="auth-container">
    <h2>Login</h2>
    <form @submit.prevent="login">
      <input v-model="email" type="email" placeholder="Email" required />
      <input v-model="password" type="password" placeholder="Password" required />
      <button type="submit">Login</button>
      <p>
        Don't have an account? <router-link to="/register">Register</router-link>
      </p>
    </form>
  </div>
</template>

<script setup>
import { ref } from "vue";
import axios from "../utils/axios";
import { useRouter } from "vue-router";

const email = ref("");
const password = ref("");
const router = useRouter();

const login = async () => {
  console.log("Login clicked:", email.value, password.value);
  try {
    const response = await axios.post("/auth/login", {
      email: email.value,
      password: password.value,
    });
    localStorage.setItem("token", response.data.token);
    router.push("/dashboard"); // Redirect to dashboard after login
  } catch (error) {
    console.error("Login failed:", error);
  }
};
</script>

<style scoped>
.auth-container {
  max-width: 400px;
  margin: 50px auto;
  text-align: center;
}
input {
  display: block;
  width: 100%;
  padding: 10px;
  margin: 10px 0;
}
button {
  padding: 10px 15px;
  background: blue;
  color: white;
  border: none;
  cursor: pointer;
}
</style>