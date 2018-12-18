package Sample;

public class Example {
	
	//Example to use the DetourConfig
	@Override
	public Config getException(DetourConfig configAnnotation) {
		return configAnnotation.onException() != null && StringUtils.isNotBlank(configAnnotation.onException().url())
				? configAnnotation.onException()
				: null;
	}
	
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.FIELD })
	@Repeatable(Configs.class)
	@Execution
	public @interface Config {
		public static String COL = StringUtils.EMPTY;
		public static String TRUE = "true";

		/**
		 * <p>A path to a nested collection param, relative to the param
		 * represented by this decorated field.
		 * <p>When provided, instead of executing this &#64;{@code Config} from the
		 * context of the decorated field's param, it will execute on each of
		 * the collection elements belonging to the collection identified by
		 * {@code col}.
		 * <p>If {@code col} is not a path to a collection element, an
		 * {@link InvalidConfigException} will be thrown.
		 */
		String col() default COL;

		/**
		 * <p>The order of execution this annotation should be executed in, with
		 * respect to other conditional annotations that are also decorating
		 * this param.
		 */
		int order() default Event.DEFAULT_ORDER_NUMBER;

		/**
		 * <p>The Command DSL URL to execute.
		 */
		String url();

		/**
		 * <p>SpEL based condition to be evaluated relative to the param's state on
		 * which this annotation is declared.
		 * <p>When the condition is evaluated as {@code true}, this
		 * &#64;{@code Config} will be executed. If not provided, this
		 * &#64;{@code Config} will always execute.
		 */
		String when() default TRUE;
	}

	/**
	 * @author Rakesh Patel
	 *
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.FIELD })
	@Repeatable(DetourConfigs.class)
	@Execution
	public @interface DetourConfig {
		Config main();

		Config onException() default @Config(url = "");

		int order() default Event.DEFAULT_ORDER_NUMBER;
	}

}
