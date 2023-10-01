package interfaces.functional;

/**
 * Функциональный интерфейс для {@link experiments.FanticAllure}
 * @param <T> Класс оборачиваемого объекта
 */
public interface FanticFunction <T> {
    String toStringBuild(T t);
}