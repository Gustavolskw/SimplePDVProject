# Usa Node.js versão LTS
FROM node:18

# Define diretório de trabalho
WORKDIR /app

# Copia apenas os arquivos de dependências para melhor uso do cache
COPY package.json package-lock.json ./

# Instala dependências
RUN npm install

# Copia todo o código do projeto (não será usado em modo dev, pois os volumes sobrescrevem)
COPY . .

# Exposição da porta do Vite para desenvolvimento
EXPOSE 5173

# Comando padrão para iniciar o Vite (será sobrescrito pelo docker-compose.yml)
CMD ["npm", "run", "dev"]
