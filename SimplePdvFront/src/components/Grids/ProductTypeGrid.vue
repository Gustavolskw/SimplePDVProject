<script setup>
import { ref, computed, onMounted, onUnmounted } from "vue";

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

const emit = defineEmits(["DELETE_ACTION"]);

function sortBy(key) {
  sortKey.value = key;
  sortOrders.value[key] *= -1;
}

function capitalize(str) {
  return str.charAt(0).toUpperCase() + str.slice(1);
}

function handleRowDelete(id) {
  emit("DELETE_ACTION", { id });
}
</script>

<template>
  <div v-if="filteredData.length" class="content-table">
    <div class="wrapper-table">
      <table>
        <thead>
          <tr>
            <th
              v-for="key in columns"
              @click="sortBy(key.key)"
              :class="{ active: sortKey == key.key }"
              :key="key.key"
            >
              {{ capitalize(key.name) }}
              <span
                class="arrow"
                :class="sortOrders[key.key] > 0 ? 'asc' : 'dsc'"
              >
              </span>
            </th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="entry in filteredData" :key="entry.id">
            <td v-for="col in columns" :key="col.key">
              <span> {{ entry[col.key] }}</span>
            </td>
            <td>
              <button class="btn" @click.prevent="handleRowDelete(entry.id)">
                <i class="bi bi-trash-fill fw-bolder fs-4 text-danger"></i>
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>

  <p v-else>No matches found.</p>
</template>

<style scoped>
table {
  width: 60%;
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
  border-bottom: 1px solid #42b983;
}

th,
td {
  min-width: 40px;
  padding: 10px 15px;
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

.small-screen-table {
  display: none;
  margin: auto;
  padding: 1rem;
}

.card-section-small-screen {
  border: 2px solid#42b983;
  padding: 1.5rem;
  margin-bottom: 2rem;
  border-radius: 10px;
}

.header-small-screen {
  gap: 2rem;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  margin-bottom: 2rem;
}
.header-small-screen > div {
  background-color: #42b983;
  padding: 0.3rem 0.5rem;
  border: 1px solid #42b983;
  border-radius: 10px;
}

.content-table {
  display: flex;
  justify-content: center;
  width: 100%;
}

.wrapper-table {
  overflow-x: auto;
  display: block;
}
@media (max-width: 500px) {
  .content-table {
    margin: 0 1rem;
    padding-left: 2rem;
    display: flex;
    width: 100%;
  }
}
</style>
