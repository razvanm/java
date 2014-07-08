// This file was auto-generated by the veyron vdl tool.
// Source(s):  arith.vdl advanced.vdl
package com.veyron2.vdl.test_arith.gen_impl;

import com.veyron2.ipc.ServerCall;
import com.veyron2.ipc.VeyronException;
import com.veyron2.vdl.Stream;
import com.veyron2.vdl.test_arith.AdvancedMath;
import com.veyron2.vdl.test_arith.AdvancedMathFactory;
import com.veyron2.vdl.test_arith.AdvancedMathService;
import com.veyron2.vdl.test_arith.Arith;
import com.veyron2.vdl.test_arith.ArithFactory;
import com.veyron2.vdl.test_arith.ArithService;
import com.veyron2.vdl.test_arith.Calculator;
import com.veyron2.vdl.test_arith.CalculatorFactory;
import com.veyron2.vdl.test_arith.CalculatorService;
import com.veyron2.vdl.test_arith.Trigonometry;
import com.veyron2.vdl.test_arith.TrigonometryFactory;
import com.veyron2.vdl.test_arith.TrigonometryService;
import com.veyron2.vdl.test_arith.VeyronConsts;
import com.veyron2.vdl.test_arith.exp.ExpService;
import com.veyron2.vdl.test_base.Args;
import com.veyron2.vdl.test_base.NestedArgs;

public class CalculatorServiceWrapper {

	private final CalculatorService service;
	private final ArithServiceWrapper arith;
	private final AdvancedMathServiceWrapper advancedMath;

	public CalculatorServiceWrapper(CalculatorService service) {
		this.arith = new ArithServiceWrapper(service);
		this.advancedMath = new AdvancedMathServiceWrapper(service);
		this.service = service;
	}
	/**
	 * Returns all tags associated with the provided method or null if the method isn't implemented
	 * by this service.
	 */
	public Object[] getMethodTags(ServerCall call, String method) { 
		{
			final Object[] tags = this.arith.getMethodTags(call, method);
			if (tags != null) return tags;
		}
		{
			final Object[] tags = this.advancedMath.getMethodTags(call, method);
			if (tags != null) return tags;
		}
		if (method == "On") {
			return new Object[]{  };
		}
		if (method == "Off") {
			return new Object[]{ "offtag" };
		}
		return null;
	}
	// Methods from interface Calculator.
	public void on(ServerCall call) throws VeyronException { 
		this.service.on(call);
	}
	public void off(ServerCall call) throws VeyronException { 
		this.service.off(call);
	}
	// Methods from sub-interface Arith.
	public int add(ServerCall call, int a, int b) throws VeyronException {
		return this.arith.add(call, a, b);
	}
	public ArithService.DivModOut divMod(ServerCall call, int a, int b) throws VeyronException {
		return this.arith.divMod(call, a, b);
	}
	public int sub(ServerCall call, Args args) throws VeyronException {
		return this.arith.sub(call, args);
	}
	public int mul(ServerCall call, NestedArgs nested) throws VeyronException {
		return this.arith.mul(call, nested);
	}
	public void genError(ServerCall call) throws VeyronException {
		this.arith.genError(call);
	}
	public void count(ServerCall call, int Start) throws VeyronException {
		this.arith.count(call, Start);
	}
	public int streamingAdd(ServerCall call) throws VeyronException {
		return this.arith.streamingAdd(call);
	}
	public Object quoteAny(ServerCall call, Object a) throws VeyronException {
		return this.arith.quoteAny(call, a);
	}
	// Methods from sub-interface AdvancedMath.
	public double sine(ServerCall call, double angle) throws VeyronException {
		return this.advancedMath.sine(call, angle);
	}
	public double cosine(ServerCall call, double angle) throws VeyronException {
		return this.advancedMath.cosine(call, angle);
	}
	public double exp(ServerCall call, double x) throws VeyronException {
		return this.advancedMath.exp(call, x);
	}
}
