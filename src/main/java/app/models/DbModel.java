package app.models;

public class DbModel<T> {
    private T entity;
    private int sqlCount;
    private double sqlQueryDuration;
    private boolean success = true;

    public DbModel() {
        this.sqlCount = 1;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public int getSqlCount() {
        return sqlCount;
    }

    public void setSqlCount(int sqlCount) {
        this.sqlCount = sqlCount;
    }

    public double getSqlQueryDuration() {
        return sqlQueryDuration;
    }

    public void setSqlQueryDuration(double sqlQueryDuration) {
        this.sqlQueryDuration = sqlQueryDuration;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
