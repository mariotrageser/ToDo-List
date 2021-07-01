FROM node AS build
WORKDIR /usr/src/todo-list-frontend
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN mkdir -p /usr/src/todo-list-frontend/dist/todo-list-frontend
RUN npm run build

FROM nginx
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=build /usr/src/todo-list-frontend/dist/todo-list-frontend /usr/share/nginx/html
