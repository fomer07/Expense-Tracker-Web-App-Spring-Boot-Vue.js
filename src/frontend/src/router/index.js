import { createRouter, createWebHistory } from "vue-router";
import LoginView from "../views/LoginView.vue";
import RegisterView from "../views/RegisterView.vue";
import DashboardView from "../views/DashboardView.vue";
import ExpensesView from "../views/ExpensesView.vue";

const routes = [
    { path: "/login", component: LoginView },
    { path: "/register", component: RegisterView },
    {
        path: "/dashboard",
        component: DashboardView,
        meta: { requiresAuth: true }, // Protect this route
    },
    { path: "/expenses", component: ExpensesView, meta: { requiresAuth: true } },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

// Navigation Guard for Authentication
router.beforeEach((to, from, next) => {
    const isAuthenticated = localStorage.getItem("token"); // Check if token exists

    if(to.meta.requiresAuth && !isAuthenticated) {
        next("/login"); // Redirect to login if not authenticated
    } else {
        next(); // Proceed normally
    }
});

export default router;