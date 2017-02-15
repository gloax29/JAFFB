package javax.flat.bind.api;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.flat.bind.make.PositionalMakeRootElem;

/**
 * @author Gloaguen Joel
 * 
 */
public class PositionalParseExpression {

	private Object newInstanceType;
	private List<FieldPositional> fd_LigneRoot;
	private Map<String, Method> map;
	private PositionalMakeRootElem element;

	public PositionalParseExpression(Object newInstanceType, List<FieldPositional> fd_LigneRoot, Map<String, Method> map,
			PositionalMakeRootElem element) {
		this.newInstanceType = newInstanceType;
		this.fd_LigneRoot = fd_LigneRoot;
		this.map = map;
		this.element = element;
	}

	public PositionalMakeRootElem getElement() {
		return element;
	}

	public Object getNewInstanceType() {
		return newInstanceType;
	}

	public List<FieldPositional> getFd_LigneRoot() {
		return fd_LigneRoot;
	}

	public Map<String, Method> getMap() {
		return map;
	}

}
