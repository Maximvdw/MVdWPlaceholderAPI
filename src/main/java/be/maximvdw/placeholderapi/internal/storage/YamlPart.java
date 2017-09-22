package be.maximvdw.placeholderapi.internal.storage;

public class YamlPart {
	private YamlPart parent = null;

	/**
	 * Get parrent
	 * 
	 * @return parrent
	 */
	public YamlPart parent() {
		return parent;
	}
	
	public void setParent(YamlPart parent){
		this.parent = parent;
	}

	/**
	 * Get builder
	 * 
	 * @return
	 */
	public YamlPart builder() {
		YamlBuilder builder = null;

		YamlPart lastPart = parent;
		do {
			if (lastPart instanceof YamlBuilder) {
				builder = (YamlBuilder) lastPart;
			} else {
				lastPart = lastPart.parent();
			}
		} while (builder == null);

		return builder;
	}
}
