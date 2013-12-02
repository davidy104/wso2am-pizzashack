package org.pizzashack.ds.converter;

import org.pizzashack.ConvertException;

/**
 * Abstract converter that handles convention between dto and domain model.
 * 
 * @author david
 * 
 * @param <M>
 *            DTO
 * @param <V>
 *            Model
 */
public interface GeneralConverter<M, V> {

	/**
	 * To DTO
	 * 
	 * @param sourceObj
	 * @param operation
	 * @param additionalSourceObj
	 * @return
	 */
	M convertFrom(V model, Object... additionalSourceObj)
			throws ConvertException;

	V convertTo(M dto, Object... additionalSourceObj) throws ConvertException;
}
