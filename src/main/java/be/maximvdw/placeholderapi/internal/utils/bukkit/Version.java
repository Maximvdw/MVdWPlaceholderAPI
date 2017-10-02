package be.maximvdw.placeholderapi.internal.utils.bukkit;

import be.maximvdw.placeholderapi.internal.utils.NumberUtils;

/**
 * Version
 * 
 * Version container
 * 
 * @project BasePlugin
 * @version 1.0
 * @author Maxim Van de Wynckel (Maximvdw)
 * @site http://www.mvdw-software.be/
 */
public class Version implements Comparable<Version> {
	/**
	 * Mayor minor and release
	 */
	private short mayor, minor, release;
	/**
	 * Alpha or beta release
	 */
	private boolean alpha, beta;

	/**
	 * Create version
	 */
	public Version() {

	}

	/**
	 * Create version from string
	 * 
	 * @param version
	 *            Version string
	 */
	public Version(String version) {
		String[] data = version.split("\\.");
		if (data.length == 3) {
			if (NumberUtils.isInteger(data[0])) {
				mayor = (short) Integer.parseInt(data[0]);
			}
			if (mayor == 0)
				alpha = true;
			if (NumberUtils.isInteger(data[1])) {
				minor = (short) Integer.parseInt(data[1]);
			}
			if (data[2].contains("b"))
				beta = true;
			if (NumberUtils.isInteger(data[2].replace("b", ""))) {
				release = (short) Integer.parseInt(data[2].replace("b", ""));
			}
		}
	}

	/**
	 * Is beta release
	 * 
	 * @return Beta release
	 */
	public boolean isBeta() {
		return beta;
	}

	/**
	 * Is alpha release
	 * 
	 * @return Alpha
	 */
	public boolean isAlpha() {
		return alpha;
	}

	/**
	 * Get mayor version
	 * 
	 * @return Mayor
	 */
	public short getMayor() {
		return mayor;
	}

	/**
	 * Set mayor version
	 * 
	 * @param mayor
	 *            Mayor
	 * @return Version
	 */
	public Version setMayor(short mayor) {
		this.mayor = mayor;
		return this;
	}

	/**
	 * Get minor version
	 * 
	 * @return Minor
	 */
	public short getMinor() {
		return minor;
	}

	/**
	 * Set minor version
	 * 
	 * @param minor
	 *            Minor
	 * @return Version
	 */
	public Version setMinor(short minor) {
		this.minor = minor;
		return this;
	}

	/**
	 * Get release
	 * 
	 * @return release
	 */
	public short getRelease() {
		return release;
	}

	/**
	 * Set release version
	 * 
	 * @param release
	 *            Release
	 * @return Version
	 */
	public Version setRelease(short release) {
		this.release = release;
		return this;
	}

	public int compare(Version otherVersion) {
		if (otherVersion.getMayor() > this.getMayor()) {
			return 1;
		} else if (otherVersion.getMayor() < this.getMayor()) {
			return -1;
		} else {
			if (otherVersion.getMinor() > this.getMinor()) {
				return 1;
			} else if (otherVersion.getMinor() < this.getMinor()) {
				return -1;
			} else {
				if (otherVersion.getRelease() > this.getRelease()) {
					return 1;
				} else if (otherVersion.getRelease() < this.getRelease()) {
					return -1;
				} else {
					if (otherVersion.isBeta() == this.isBeta()) {
						return 0;
					} else {
						if (otherVersion.isBeta())
							return -1;
						else
							return 1;
					}
				}
			}
		}
	}

	/**
	 * Version to string
	 */
	public String toString() {
		String version = mayor + "." + minor + "." + release;
		if (isBeta())
			version += "b";
		return version;
	}

	@Override
	public int compareTo(Version otherVersion) {
		if (otherVersion.getMayor() > this.getMayor()) {
			return 1;
		} else if (otherVersion.getMayor() < this.getMayor()) {
			return -1;
		} else {
			if (otherVersion.getMinor() > this.getMinor()) {
				return 1;
			} else if (otherVersion.getMinor() < this.getMinor()) {
				return -1;
			} else {
				if (otherVersion.getRelease() > this.getRelease()) {
					return 1;
				} else if (otherVersion.getRelease() < this.getRelease()) {
					return -1;
				} else {
					if (otherVersion.isBeta() == this.isBeta()) {
						return 0;
					} else {
						if (otherVersion.isBeta())
							return -1;
						else
							return 1;
					}
				}
			}
		}
	}
}
