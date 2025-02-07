<script setup>
import { ref, computed, onMounted, onUnmounted } from "vue";
import { formatCurrency } from "@/Util/Currency";

const props = defineProps({
  data: Array,
  columns: Array,
  filterKey: String,
});

const sortKey = ref("");
const sortOrders = ref(
  props.columns.reduce((o, key) => ((o[key.key] = 1), o), {})
);

const filteredData = computed(() => {
  let { data, filterKey } = props;
  if (filterKey) {
    filterKey = filterKey.toLowerCase();
    data = data.filter((row) => {
      return Object.keys(row).some((key) => {
        return String(row[key]).toLowerCase().indexOf(filterKey) > -1;
      });
    });
  }
  const key = sortKey.value;
  if (key) {
    const order = sortOrders.value[key];
    data = data.slice().sort((a, b) => {
      a = a[key];
      b = b[key];
      return (a === b ? 0 : a > b ? 1 : -1) * order;
    });
  }
  return data;
});

const emit = defineEmits([
  "DELETE_ACTION",
  "UPDATE_ACTION",
  "REACTIVATE_ACTION",
]);

function sortBy(key) {
  sortKey.value = key;
  sortOrders.value[key] *= -1;
}

function capitalize(str) {
  return str.charAt(0).toUpperCase() + str.slice(1);
}

// Function to check screen width
const checkScreenSize = () => {
  //colapseNav.value = window.innerWidth < 700;
  //console.log(window.innerWidth);
};

onMounted(() => {
  checkScreenSize();
  window.addEventListener("resize", checkScreenSize);
});

// Cleanup event listener on unmount
onUnmounted(() => {
  window.removeEventListener("resize", checkScreenSize);
});

function handleReactivationAction(id) {
  emit("REACTIVATE_ACTION", { id });
}
function handleRowDelete(id) {
  emit("DELETE_ACTION", { id });
}
function handleRowUpdate(id) {
  emit("UPDATE_ACTION", { id });
}
</script>

<template>
  <table v-if="filteredData.length">
    <thead>
      <tr>
        <th
          v-for="key in columns"
          @click="sortBy(key.key)"
          :class="{ active: sortKey == key.key }"
          :key="key.key"
        >
          {{ capitalize(key.name) }}
          <span class="arrow" :class="sortOrders[key.key] > 0 ? 'asc' : 'dsc'">
          </span>
        </th>
        <th>Ações</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="entry in filteredData" :key="entry.id">
        <td v-for="col in columns" :key="col.key">
          <span v-if="col.type === 1">
            {{ formatCurrency(entry[col.key]) }}</span
          >
          <span
            v-else-if="col.key == 'status'"
            class="fw-bold"
            :class="{
              'text-danger': entry[col.key] == false,
              'text-success': entry[col.key] == true,
            }"
          >
            {{ entry[col.key] == true ? "Ativo" : "Inativo" }}</span
          >
          <span v-else> {{ entry[col.key] }}</span>
        </td>
        <td>
          <button
            class="btn"
            @click.prevent="handleRowDelete(entry.id)"
            v-if="entry.status == true"
          >
            <i class="bi bi-trash-fill fw-bolder fs-4 text-danger"></i>
          </button>
          <button
            class="btn"
            v-else
            @click.prevent="handleReactivationAction(entry.id)"
          >
            <i class="bi bi-bootstrap-reboot fw-bolder fs-4 text-success"></i>
          </button>
          <button class="btn" @click.prevent="handleRowUpdate(entry.id)">
            <i class="bi bi-pencil-square fw-bolder fs-4 text-secondary"></i>
          </button>
        </td>
      </tr>
    </tbody>
  </table>
  <p v-else>No matches found.</p>
</template>

<style>
table {
  border: 2px solid #42b983;
  border-radius: 3px;
  background-color: #fff;
}

th {
  background-color: #42b983;
  color: rgba(255, 255, 255, 0.66);
  cursor: pointer;
  user-select: none;
}

td {
  background-color: #f9f9f9;
}

th,
td {
  min-width: 120px;
  padding: 10px 20px;
}

th.active {
  color: #fff;
}

th.active .arrow {
  opacity: 1;
}

.arrow {
  display: inline-block;
  vertical-align: middle;
  width: 0;
  height: 0;
  margin-left: 5px;
  opacity: 0.66;
}

.arrow.asc {
  border-left: 4px solid transparent;
  border-right: 4px solid transparent;
  border-bottom: 4px solid #fff;
}

.arrow.dsc {
  border-left: 4px solid transparent;
  border-right: 4px solid transparent;
  border-top: 4px solid #fff;
}

@media (max-width: 1200px) {
  table {
    display: none;
  }
}
</style>
