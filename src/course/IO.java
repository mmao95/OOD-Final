package course;

public interface IO<Type> {
    Type readFromFile(String path);

    void writeToFile(String path);
}
