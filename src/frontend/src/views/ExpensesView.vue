<template>
  <div class="container mx-auto p-4">
    <h2 class="text-2xl font-bold text-center mb-4">My Expenses</h2>

    <div class="flex gap-4 mb-4">
      <input v-model="searchTerm" class="border p-2 rounded w-1/4" placeholder="Search..." />
      <select v-model="categoryFilter" class="border p-2 rounded w-1/4">
        <option value="">All Categories</option>
        <option v-for="category in categories" :key="category" :value="category">{{ category }}</option>
      </select>
      <input v-model="minAmount" type="number" class="border p-2 rounded w-1/4" placeholder="Min Amount" />
      <input v-model="maxAmount" type="number" class="border p-2 rounded w-1/4" placeholder="Max Amount" />
    </div>

    <ExpenseForm v-if="showForm" :expenseData="editingExpense" @saved="saveExpense" />
    <button @click="showForm = !showForm" class="bg-blue-500 text-white p-2 rounded">{{ showForm ? "Cancel" : "Add Expense" }}</button>

    <ul class="mt-4">
      <li v-for="expense in filteredExpenses" :key="expense.id" class="flex justify-between bg-gray-100 p-2 rounded mt-2">
        <span>{{ expense.title }} - ${{ expense.amount }} ({{ expense.category }})</span>
        <div>
          <button @click="editExpense(expense)" class="bg-yellow-500 text-white p-1 rounded mr-2">Edit</button>
          <button @click="deleteExpense(expense.id)" class="bg-red-500 text-white p-1 rounded">Delete</button>
        </div>
      </li>
    </ul>

    <ExpenseChart :expenses="filteredExpenses" />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import axios from "../utils/axios";
import ExpenseForm from "../components/ExpenseForm.vue";
import ExpenseChart from "../components/ExpenseChart.vue";

const expenses = ref([]);
const showForm = ref(false);
const editingExpense = ref(null); // TODO what is ref ?
const searchTerm = ref("");
const categoryFilter = ref("");
const minAmount = ref("");
const maxAmount = ref("");

const categories = ref(["Food", "Transport", "Entertainment", "Bills", "Shopping"]);

const fetchExpenses = async () => { // TODO learn why we assign to variable a function
  console.log("Fetching Expenses");
  try {
    const response = await axios.get("/expenses");
    expenses.value = response.data;
  } catch (error) {
    console.error("Error fetching expenses:", error);
  }
};

const saveExpense = async (expense) => {
  console.log("Saving expense:", expense);
  try {
    if (expense.id) {
      await axios.put(`/expenses/${expense.id}`, expense);
    } else {
      await axios.post("/expenses", expense);
    }
    showForm.value = false;
    editingExpense.value = null;
    fetchExpenses(); // TODO missing await for an async function call
  } catch (error) {
    console.error("Error saving expense:", error);
  }
};

const editExpense = (expense) => {
  editingExpense.value = { ...expense }; // TODO what is { ...expense } ?
  showForm.value = true;
};

const deleteExpense = async (id) => {
  try {
    await axios.delete(`/expenses/${id}`);
    fetchExpenses();
  } catch (error) {
    console.error("Error deleting expense:", error);
  }
};

// Filter expenses based on search, category, and amount range
const filteredExpenses = computed(() => {
  return expenses.value.filter(expense => {
    return (
        (!searchTerm.value || expense.title.toLowerCase().includes(searchTerm.value.toLowerCase())) &&
        (!categoryFilter.value || expense.category === categoryFilter.value) &&
        (!minAmount.value || expense.amount >= minAmount.value) &&
        (!maxAmount.value || expense.amount <= maxAmount.value)
    );
  });
});

onMounted(fetchExpenses);
</script>

<style scoped>
.filters {
  display: flex;
  gap: 10px;
  margin-bottom: 10px;
}
input, select {
  padding: 5px;
}
h2 {
  text-align: center;
}
ul {
  list-style: none;
  padding: 0;
}
li {
  display: flex;
  justify-content: space-between;
  padding: 8px;
  border-bottom: 1px solid #ddd;
}
button {
  margin-left: 5px;
  padding: 5px 10px;
  cursor: pointer;
}
</style>