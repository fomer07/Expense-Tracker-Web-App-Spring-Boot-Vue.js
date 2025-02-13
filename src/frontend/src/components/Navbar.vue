<template>
  <nav class="flex justify-between bg-gray-900 text-white p-4">
    <div>
      <router-link to="/dashboard" class="mr-4">Dashboard</router-link>
      <router-link to="/expenses">Expenses</router-link>
    </div>
    <button @click="confirmLogout" class="bg-red-500 px-3 py-1 rounded">Logout</button>
  </nav>
</template>

<script setup>
import { useRouter } from "vue-router";

const router = useRouter();

const confirmLogout = () => {
  if (confirm("Are you sure you want to logout?")) {
    localStorage.removeItem("token");
    router.push("/login");
  }
};
</script>

<style scoped>
nav {
  display: flex;
  justify-content: space-between;
  padding: 10px;
  background: #333;
  color: white;
}
a {
  color: white;
  text-decoration: none;
  margin-right: 10px;
}
button {
  background: red;
  color: white;
  border: none;
  padding: 5px 10px;
  cursor: pointer;
}
</style>