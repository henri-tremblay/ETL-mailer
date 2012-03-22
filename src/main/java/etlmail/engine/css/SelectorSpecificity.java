package etlmail.engine.css;

class SelectorSpecificity implements Comparable<SelectorSpecificity> {
	private final int ids;
	private final int classes;
	private final int types;

	public SelectorSpecificity(int ids, int classes, int types) {
		this.ids = ids;
		this.classes = classes;
		this.types = types;
	}

	public static class Builder {
		private int ids;
		private int classes;
		private int types;

		public Builder addId(int count) {
			ids += count;
			return this;
		}

		public Builder addClass(int count) {
			classes += count;
			return this;
		}

		public Builder addType(int count) {
			types += count;
			return this;
		}

		public SelectorSpecificity asSpecificity() {
			return new SelectorSpecificity(ids, classes, types);
		}
	}

	@Override
	public int compareTo(SelectorSpecificity o) {
		int result = ids - o.ids;
		if (result == 0) {
			result = classes - o.classes;
		}
		if (result == 0) {
			result = types - o.types;
		}
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + classes;
		result = (prime * result) + ids;
		result = (prime * result) + types;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final SelectorSpecificity other = (SelectorSpecificity) obj;
		if (classes != other.classes) {
			return false;
		}
		if (ids != other.ids) {
			return false;
		}
		if (types != other.types) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "SelectorSpecificit(" + ids + ", " + classes + ", " + types
				+ ")";
	}

}
