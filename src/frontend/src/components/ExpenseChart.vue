<template>
  <div class="chart-container">
    <canvas ref="chartCanvas"></canvas>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import Chart from "chart.js/auto";

const props = defineProps(["expenses"]);
const chartCanvas = ref(null);
let chartInstance = null;

const createChart = () => {
  if (chartInstance) {
    chartInstance.destroy();
  }

  const categories = {};
  props.expenses.forEach(expense => {
    categories[expense.category] = (categories[expense.category] || 0) + expense.amount;
  });

  chartInstance = new Chart(chartCanvas.value, {
    type: "doughnut",
    data: {
      labels: Object.keys(categories),
      datasets: [
        {
          data: Object.values(categories),
          backgroundColor: ["#ff6384", "#36a2eb", "#ffcd56", "#4bc0c0", "#9966ff"],
        },
      ],
    },
  });
};

onMounted(createChart);
watch(() => props.expenses, createChart, { deep: true });
</script>

<style scoped>
.chart-container {
  width: 100%;
  max-width: 400px;
  margin: auto;
}
</style>