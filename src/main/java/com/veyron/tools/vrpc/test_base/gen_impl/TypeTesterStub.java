// This file was auto-generated by the veyron vdl tool.
// Source(s):  test_base.vdl
package com.veyron.tools.vrpc.test_base.gen_impl;

/* Client stub for interface: TypeTester. */
public final class TypeTesterStub implements com.veyron.tools.vrpc.test_base.TypeTester {
    private static final java.lang.String vdlIfacePathOpt = "veyron/tools/vrpc/test_base/TypeTester";
    private final com.veyron2.ipc.Client client;
    private final java.lang.String veyronName;

    
    

    public TypeTesterStub(final com.veyron2.ipc.Client client, final java.lang.String veyronName) {
        this.client = client;
        this.veyronName = veyronName;
        
        
    }

    // Methods from interface TypeTester.


    
    public boolean echoBool(final com.veyron2.ipc.Context context, final boolean I1) throws com.veyron2.ipc.VeyronException {
        return echoBool(context, I1, null);
    }
    
    public boolean echoBool(final com.veyron2.ipc.Context context, final boolean I1, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, TypeTesterStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{ I1 };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "echoBool", inArgs, veyronOpts);

        // Finish the call.
        
        

         
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{
            
            new com.google.common.reflect.TypeToken<java.lang.Boolean>() {
                private static final long serialVersionUID = 1L;
            },
            
        };
        final java.lang.Object[] results = call.finish(resultTypes);
         
        return (java.lang.Boolean)results[0];
         

         

        
    }

    
    public float echoFloat32(final com.veyron2.ipc.Context context, final float I1) throws com.veyron2.ipc.VeyronException {
        return echoFloat32(context, I1, null);
    }
    
    public float echoFloat32(final com.veyron2.ipc.Context context, final float I1, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, TypeTesterStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{ I1 };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "echoFloat32", inArgs, veyronOpts);

        // Finish the call.
        
        

         
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{
            
            new com.google.common.reflect.TypeToken<java.lang.Float>() {
                private static final long serialVersionUID = 1L;
            },
            
        };
        final java.lang.Object[] results = call.finish(resultTypes);
         
        return (java.lang.Float)results[0];
         

         

        
    }

    
    public double echoFloat64(final com.veyron2.ipc.Context context, final double I1) throws com.veyron2.ipc.VeyronException {
        return echoFloat64(context, I1, null);
    }
    
    public double echoFloat64(final com.veyron2.ipc.Context context, final double I1, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, TypeTesterStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{ I1 };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "echoFloat64", inArgs, veyronOpts);

        // Finish the call.
        
        

         
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{
            
            new com.google.common.reflect.TypeToken<java.lang.Double>() {
                private static final long serialVersionUID = 1L;
            },
            
        };
        final java.lang.Object[] results = call.finish(resultTypes);
         
        return (java.lang.Double)results[0];
         

         

        
    }

    
    public int echoInt32(final com.veyron2.ipc.Context context, final int I1) throws com.veyron2.ipc.VeyronException {
        return echoInt32(context, I1, null);
    }
    
    public int echoInt32(final com.veyron2.ipc.Context context, final int I1, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, TypeTesterStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{ I1 };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "echoInt32", inArgs, veyronOpts);

        // Finish the call.
        
        

         
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{
            
            new com.google.common.reflect.TypeToken<java.lang.Integer>() {
                private static final long serialVersionUID = 1L;
            },
            
        };
        final java.lang.Object[] results = call.finish(resultTypes);
         
        return (java.lang.Integer)results[0];
         

         

        
    }

    
    public long echoInt64(final com.veyron2.ipc.Context context, final long I1) throws com.veyron2.ipc.VeyronException {
        return echoInt64(context, I1, null);
    }
    
    public long echoInt64(final com.veyron2.ipc.Context context, final long I1, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, TypeTesterStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{ I1 };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "echoInt64", inArgs, veyronOpts);

        // Finish the call.
        
        

         
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{
            
            new com.google.common.reflect.TypeToken<java.lang.Long>() {
                private static final long serialVersionUID = 1L;
            },
            
        };
        final java.lang.Object[] results = call.finish(resultTypes);
         
        return (java.lang.Long)results[0];
         

         

        
    }

    
    public java.lang.String echoString(final com.veyron2.ipc.Context context, final java.lang.String I1) throws com.veyron2.ipc.VeyronException {
        return echoString(context, I1, null);
    }
    
    public java.lang.String echoString(final com.veyron2.ipc.Context context, final java.lang.String I1, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, TypeTesterStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{ I1 };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "echoString", inArgs, veyronOpts);

        // Finish the call.
        
        

         
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{
            
            new com.google.common.reflect.TypeToken<java.lang.String>() {
                private static final long serialVersionUID = 1L;
            },
            
        };
        final java.lang.Object[] results = call.finish(resultTypes);
         
        return (java.lang.String)results[0];
         

         

        
    }

    
    public byte echoByte(final com.veyron2.ipc.Context context, final byte I1) throws com.veyron2.ipc.VeyronException {
        return echoByte(context, I1, null);
    }
    
    public byte echoByte(final com.veyron2.ipc.Context context, final byte I1, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, TypeTesterStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{ I1 };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "echoByte", inArgs, veyronOpts);

        // Finish the call.
        
        

         
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{
            
            new com.google.common.reflect.TypeToken<java.lang.Byte>() {
                private static final long serialVersionUID = 1L;
            },
            
        };
        final java.lang.Object[] results = call.finish(resultTypes);
         
        return (java.lang.Byte)results[0];
         

         

        
    }

    
    public int echoUInt32(final com.veyron2.ipc.Context context, final int I1) throws com.veyron2.ipc.VeyronException {
        return echoUInt32(context, I1, null);
    }
    
    public int echoUInt32(final com.veyron2.ipc.Context context, final int I1, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, TypeTesterStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{ I1 };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "echoUInt32", inArgs, veyronOpts);

        // Finish the call.
        
        

         
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{
            
            new com.google.common.reflect.TypeToken<java.lang.Integer>() {
                private static final long serialVersionUID = 1L;
            },
            
        };
        final java.lang.Object[] results = call.finish(resultTypes);
         
        return (java.lang.Integer)results[0];
         

         

        
    }

    
    public long echoUInt64(final com.veyron2.ipc.Context context, final long I1) throws com.veyron2.ipc.VeyronException {
        return echoUInt64(context, I1, null);
    }
    
    public long echoUInt64(final com.veyron2.ipc.Context context, final long I1, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, TypeTesterStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{ I1 };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "echoUInt64", inArgs, veyronOpts);

        // Finish the call.
        
        

         
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{
            
            new com.google.common.reflect.TypeToken<java.lang.Long>() {
                private static final long serialVersionUID = 1L;
            },
            
        };
        final java.lang.Object[] results = call.finish(resultTypes);
         
        return (java.lang.Long)results[0];
         

         

        
    }

    
    public void inputArray(final com.veyron2.ipc.Context context, final byte[] I1) throws com.veyron2.ipc.VeyronException {
         inputArray(context, I1, null);
    }
    
    public void inputArray(final com.veyron2.ipc.Context context, final byte[] I1, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, TypeTesterStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{ I1 };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "inputArray", inArgs, veyronOpts);

        // Finish the call.
        
        

        
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{};
        call.finish(resultTypes);
         

        
    }

    
    public void inputMap(final com.veyron2.ipc.Context context, final java.util.Map<java.lang.Byte, java.lang.Byte> I1) throws com.veyron2.ipc.VeyronException {
         inputMap(context, I1, null);
    }
    
    public void inputMap(final com.veyron2.ipc.Context context, final java.util.Map<java.lang.Byte, java.lang.Byte> I1, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, TypeTesterStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{ I1 };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "inputMap", inArgs, veyronOpts);

        // Finish the call.
        
        

        
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{};
        call.finish(resultTypes);
         

        
    }

    
    public void inputSlice(final com.veyron2.ipc.Context context, final byte[] I1) throws com.veyron2.ipc.VeyronException {
         inputSlice(context, I1, null);
    }
    
    public void inputSlice(final com.veyron2.ipc.Context context, final byte[] I1, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, TypeTesterStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{ I1 };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "inputSlice", inArgs, veyronOpts);

        // Finish the call.
        
        

        
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{};
        call.finish(resultTypes);
         

        
    }

    
    public void inputStruct(final com.veyron2.ipc.Context context, final com.veyron.tools.vrpc.test_base.Struct I1) throws com.veyron2.ipc.VeyronException {
         inputStruct(context, I1, null);
    }
    
    public void inputStruct(final com.veyron2.ipc.Context context, final com.veyron.tools.vrpc.test_base.Struct I1, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, TypeTesterStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{ I1 };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "inputStruct", inArgs, veyronOpts);

        // Finish the call.
        
        

        
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{};
        call.finish(resultTypes);
         

        
    }

    
    public byte[] outputArray(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
        return outputArray(context, null);
    }
    
    public byte[] outputArray(final com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, TypeTesterStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{  };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "outputArray", inArgs, veyronOpts);

        // Finish the call.
        
        

         
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{
            
            new com.google.common.reflect.TypeToken<byte[]>() {
                private static final long serialVersionUID = 1L;
            },
            
        };
        final java.lang.Object[] results = call.finish(resultTypes);
         
        return (byte[])results[0];
         

         

        
    }

    
    public java.util.Map<java.lang.Byte, java.lang.Byte> outputMap(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
        return outputMap(context, null);
    }
    
    public java.util.Map<java.lang.Byte, java.lang.Byte> outputMap(final com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, TypeTesterStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{  };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "outputMap", inArgs, veyronOpts);

        // Finish the call.
        
        

         
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{
            
            new com.google.common.reflect.TypeToken<java.util.Map<java.lang.Byte, java.lang.Byte>>() {
                private static final long serialVersionUID = 1L;
            },
            
        };
        final java.lang.Object[] results = call.finish(resultTypes);
         
        return (java.util.Map<java.lang.Byte, java.lang.Byte>)results[0];
         

         

        
    }

    
    public byte[] outputSlice(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
        return outputSlice(context, null);
    }
    
    public byte[] outputSlice(final com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, TypeTesterStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{  };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "outputSlice", inArgs, veyronOpts);

        // Finish the call.
        
        

         
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{
            
            new com.google.common.reflect.TypeToken<byte[]>() {
                private static final long serialVersionUID = 1L;
            },
            
        };
        final java.lang.Object[] results = call.finish(resultTypes);
         
        return (byte[])results[0];
         

         

        
    }

    
    public com.veyron.tools.vrpc.test_base.Struct outputStruct(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
        return outputStruct(context, null);
    }
    
    public com.veyron.tools.vrpc.test_base.Struct outputStruct(final com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, TypeTesterStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{  };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "outputStruct", inArgs, veyronOpts);

        // Finish the call.
        
        

         
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{
            
            new com.google.common.reflect.TypeToken<com.veyron.tools.vrpc.test_base.Struct>() {
                private static final long serialVersionUID = 1L;
            },
            
        };
        final java.lang.Object[] results = call.finish(resultTypes);
         
        return (com.veyron.tools.vrpc.test_base.Struct)results[0];
         

         

        
    }

    
    public void noArguments(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
         noArguments(context, null);
    }
    
    public void noArguments(final com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, TypeTesterStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{  };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "noArguments", inArgs, veyronOpts);

        // Finish the call.
        
        

        
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{};
        call.finish(resultTypes);
         

        
    }

    
    public com.veyron.tools.vrpc.test_base.TypeTester.MultipleArgumentsOut multipleArguments(final com.veyron2.ipc.Context context, final int I1, final int I2) throws com.veyron2.ipc.VeyronException {
        return multipleArguments(context, I1, I2, null);
    }
    
    public com.veyron.tools.vrpc.test_base.TypeTester.MultipleArgumentsOut multipleArguments(final com.veyron2.ipc.Context context, final int I1, final int I2, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, TypeTesterStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{ I1, I2 };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "multipleArguments", inArgs, veyronOpts);

        // Finish the call.
        
        

         
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{
            
            new com.google.common.reflect.TypeToken<java.lang.Integer>() {
                private static final long serialVersionUID = 1L;
            },
            
            new com.google.common.reflect.TypeToken<java.lang.Integer>() {
                private static final long serialVersionUID = 1L;
            },
            
        };
        final java.lang.Object[] results = call.finish(resultTypes);
        
        final com.veyron.tools.vrpc.test_base.TypeTester.MultipleArgumentsOut ret = new com.veyron.tools.vrpc.test_base.TypeTester.MultipleArgumentsOut();
            
        ret.o1 = (java.lang.Integer)results[0];
            
        ret.o2 = (java.lang.Integer)results[1];
             
        return ret;
         

         

        
    }

    
    public com.veyron2.vdl.ClientStream<java.lang.Void,java.lang.Boolean, java.lang.Void> streamingOutput(final com.veyron2.ipc.Context context, final int NumStreamItems, final boolean StreamItem) throws com.veyron2.ipc.VeyronException {
        return streamingOutput(context, NumStreamItems, StreamItem, null);
    }
    
    public com.veyron2.vdl.ClientStream<java.lang.Void,java.lang.Boolean, java.lang.Void> streamingOutput(final com.veyron2.ipc.Context context, final int NumStreamItems, final boolean StreamItem, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, TypeTesterStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{ NumStreamItems, StreamItem };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "streamingOutput", inArgs, veyronOpts);

        // Finish the call.
        
         
        return new com.veyron2.vdl.ClientStream<java.lang.Void, java.lang.Boolean, java.lang.Void>() {
            @Override
            public void send(final java.lang.Void item) throws com.veyron2.ipc.VeyronException {
                call.send(item);
            }
            @Override
            public java.lang.Boolean recv() throws java.io.EOFException, com.veyron2.ipc.VeyronException {
                final com.google.common.reflect.TypeToken<?> type = new com.google.common.reflect.TypeToken<java.lang.Boolean>() {
                    private static final long serialVersionUID = 1L;
                };
                final java.lang.Object result = call.recv(type);
                try {
                    return (java.lang.Boolean)result;
                } catch (java.lang.ClassCastException e) {
                    throw new com.veyron2.ipc.VeyronException("Unexpected result type: " + result.getClass().getCanonicalName());
                }
            }
            @Override
            public java.lang.Void finish() throws com.veyron2.ipc.VeyronException {
                
                final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{};
                call.finish(resultTypes);
                return null;
                 
            }
        };
        
    }





}
