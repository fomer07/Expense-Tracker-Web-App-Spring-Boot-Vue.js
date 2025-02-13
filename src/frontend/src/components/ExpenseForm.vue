<template>
  <form @submit.prevent="saveExpense">
    <input v-model="expense.title" type="text" placeholder="Title" required />
    <input v-model="expense.amount" type="number" placeholder="Amount" required />
    <select v-model="expense.category" required>
      <option value="">Select Category</option>
      <option v-for="category in categories" :key="category" :value="category">{{ category }}</option>
    </select>
    <button type="submit">{{ isEdit ? "Update" : "Add" }} Expense</button>
  </form>
</template>

<script setup>
import { ref, defineProps, defineEmits } from "vue";

const props = defineProps(["expenseData"]);
const emit = defineEmits(["saved"]);

const categories = ["Food", "Transport", "Entertainment", "Bills", "Shopping"];
const expense = ref(props.expenseData || { title: "", amount: 0, category: "" });
const isEdit = ref(!!props.expenseData);

const saveExpense = () => {
  emit("saved", expense.value);
};
</script>

<style scoped>
form {
  display: flex;
  flex-direction: column;
  max-width: 300px;
  margin: auto;
}
input {
  margin-bottom: 10px;
  padding: 8px;
}
button {
  padding: 10px;
  background: blue;
  color: white;
  border: none;
  cursor: pointer;
}
</style>