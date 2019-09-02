package i.part.app.course.todo.board.data;

public class Task {
    String name;
    int todo;
    int totalTasks;
    String imageUrl = "https://images-na.ssl-images-amazon.com/images/I/71QMsWSZqaL._SL1152_.jpg";
    int remaningTasks;
    String status;

    public Task(String name, int todo, int totalTasks, int remaningTasks, String status) {
        this.name = name;
        this.todo = todo;
        this.totalTasks = totalTasks;
        this.remaningTasks = remaningTasks;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTodo() {
        return todo;
    }

    public void setTodo(int todo) {
        this.todo = todo;
    }

    public int getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(int totalTasks) {
        this.totalTasks = totalTasks;
    }

    public int getRemaningTasks() {
        return remaningTasks;
    }

    public void setRemaningTasks(int remaningTasks) {
        this.remaningTasks = remaningTasks;
    }
}
