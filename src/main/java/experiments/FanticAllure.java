package experiments;

import interfaces.functional.FanticFunction;

/**
 * Обертка для корректного отображения значений входных параметров тестового метода.
 * При определении в конструктор нужно передать объект и функцию, с помощью которой из этого объекта можно получить
 * String значение параметра для отображения в отчете.
 * @param <T> Класс оборачиваемого объекта.
 */
public class FanticAllure<T> {
    private final T t;
    private final FanticFunction<T> function;

    public FanticAllure(T t, FanticFunction<T> function) {
        this.t = t;
        this.function = function;
    }

    // Переопределяем метод возвращая значение переданной функции
    @Override
    public String toString() {
        return function.toStringBuild(t);
    }

    /**
     * Возвращает помещенный в фантик объект
     * @return Помещенный в фантик объект
     */
    public T getObject () {
        return this.t;
    }
}