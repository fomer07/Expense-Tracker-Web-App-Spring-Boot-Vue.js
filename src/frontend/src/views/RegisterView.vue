<template>
  <div class="auth-container">
    <h2>Register</h2>
    <form @submit.prevent="register">
      <input v-model="name" type="text" placeholder="Full Name" required />
      <input v-model="email" type="email" placeholder="Email" required />
      <input v-model="password" type="password" placeholder="Password" required />
      <button type="submit">Register</button>
      <p>
        Already have an account? <router-link to="/login">Login</router-link>
      </p>
      </form>
  </div>
</template>

<script setup>
import { ref } from "vue";
import axios from "../utils/axios";
import { useRouter } from "vue-router";
import router from "@/router";

const name = ref("");
const email = ref("");
const password = ref("");

const register = async () => {
  console.log("Register clicked:", name.value, email.value, password.value);
  try {
    await axios.post("/auth/register", {
      name: name.value,
      email: email.value,
      password: password.value,
    });
    router.push("/login"); // Redirect to login after registration
  } catch (error) {
    console.error("Register failed:", error);
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
  background: green;
  color: white;
  border: none;
  cursor: pointer;
}
</style>