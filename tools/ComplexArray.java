package tools;

public class ComplexArray {
	private Array var;
	private Array name;
	private Array type;

	public ComplexArray() {
	}

	public void add(String name, Object var, String type) {
		this.name.add(name);
		this.var.add(var);
		this.type.add(type);
	}

	public Array getPos(int position) {
		Array result = null;
		if (position >= 0 && position < this.var.len()) {
			result = new Array();
			result.add(this.name.get(position));
			result.add(this.var.get(position));
			result.add(this.type.get(position));
		}
		return result;
	}

	public boolean modPosition(int position, String name, Object var, String type) {
		boolean isModified = false;
		if (position >= 0 && position < this.name.len()) {
			this.name.modify(name, position);
			this.var.modify(var, position);
			this.type.modify(type, position);
			isModified = true;
		}
		return isModified;
	}

	public void clear() {
		this.name.del();
		this.var.del();
		this.type.del();
	}

	public boolean deletePos(int position) {
		boolean isDeleted = false;
		if (position >= 0 && position < this.name.len()) {
			this.name.del(position);
			this.var.del(position);
			this.type.del(position);
			isDeleted = true;
		}
		return isDeleted;
	}

	public int len() {
		return this.name.len();
	}
}
