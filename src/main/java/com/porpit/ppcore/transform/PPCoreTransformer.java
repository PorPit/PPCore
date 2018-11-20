package com.porpit.ppcore.transform;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.io.ByteStreams;
import com.porpit.ppcore.PPCore;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.tree.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public abstract class PPCoreTransformer implements IClassTransformer {

    public PPCoreTransformer() {
        initTransformers();
    }

    private final ArrayList<Transformer> transformers = new ArrayList<Transformer>();

    protected abstract void initTransformers();

    protected void addTransformer(Transformer transformer) {
        transformers.add(transformer);
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (name.contains("com.porpit"))
            return basicClass;
        return transform(transformedName, basicClass);
    }

    public byte[] transform(String name, byte[] basicClass) {
        int i = 0;
        while (i < transformers.size()) {
            if (transformers.get(i).is(name)) {
              // ClassNode classNode = new ClassNode();
                //ClassReader classReader = new ClassReader(basicClass);
                System.out.println("[" + PPCore.MODID + "] Patching " + transformers.get(i).className + "");
                byte[] classData = transformers.get(i).transform(basicClass);
                //classReader.accept(classNode, 0);
                ClassReader reader = new ClassReader(classData);
                ClassNode node = new ClassNode();
                reader.accept(node, 0);

                ClassWriter writer = new ClassWriter(0);
                node.accept(writer);
                return writer.toByteArray();
                // transformers.remove(i);
            } else
                i++;
        }
        return basicClass;
    }


    public static byte[] spliceClasses(final byte[] data, final String className, final String... methods) {
        ClassReader reader = new ClassReader(data);
        ClassNode nodeOrig = new ClassNode();
        reader.accept(nodeOrig, 0);
        ClassNode nodeNew = spliceClasses(nodeOrig, className, methods);
        ClassWriter writer = new ClassWriter(0);
        nodeNew.accept(writer);
        return writer.toByteArray();
    }


    /**
     @asiekierka
     */
    public static ClassNode getClassNode(Class cl,String className){
        try{
            ClassNode classNode=new ClassNode();
            String tfClassName=TransformerNames.patchClassName(className);
            InputStream stream = cl.getClassLoader().getResourceAsStream(tfClassName.replace('.', '/')+".class");
            ClassReader classReader=new ClassReader(ByteStreams.toByteArray(stream));
            classReader.accept(classNode, 0);
            return classNode;
        }catch (IOException e){
            System.out.println("faild to load:"+className);
        }catch (NullPointerException e){
            System.out.println("class:"+className +" not found");
        }
        return null;
    }

    /**
     @asiekierka
     */
    public static ClassNode spliceClasses(final ClassNode data, final String className, final String... methods) {
        try (InputStream stream = PPCoreTransformer.class.getClassLoader().getResourceAsStream(className.replace('.', '/') + ".class")) {
            return spliceClasses(data, ByteStreams.toByteArray(stream), className, methods);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     @asiekierka
     */
    public static ClassNode spliceClasses(final ClassNode nodeData, final byte[] dataSplice, final String className, final String... methods) {
        // System.out.println("Splicing from " + className + " to " + targetClassName)
        if (dataSplice == null) {
            throw new RuntimeException("Class " + className + " not found! This is a FoamFix bug!");
        }

        final Set<String> methodSet = Sets.newHashSet(methods);
        final List<String> methodList = Lists.newArrayList(methods);

        final ClassReader readerSplice = new ClassReader(dataSplice);
        final String className2 = className.replace('.', '/');
        final String targetClassName2 = nodeData.name;
        final String targetClassName = targetClassName2.replace('/', '.');
        final Remapper remapper = new Remapper() {
            public String map(final String name) {
                return className2.equals(name) ? targetClassName2 : name;
            }
        };

        ClassNode nodeSplice = new ClassNode();
        readerSplice.accept(new ClassRemapper(nodeSplice, remapper), ClassReader.EXPAND_FRAMES);
        for (String s : nodeSplice.interfaces) {
            if (methodSet.contains(s)) {
                nodeData.interfaces.add(s);
                System.out.println("Added INTERFACE: " + s);
            }
        }

        for (int i = 0; i < nodeSplice.methods.size(); i++) {
            for(int k=0;k<methodSet.size();k++){

            }
            if (methodSet.contains(nodeSplice.methods.get(i).name)) {
                MethodNode mn = nodeSplice.methods.get(i);
                String srgName=methodList.get((methodList.indexOf(mn.name)) & (~1));

                String notchName=Transformer.patchMethodName(targetClassName2, srgName, mn.desc);
                String notchDesc=Transformer.patchDESC(mn.desc);
                boolean added = false;
                mn.name=notchName;
                mn.desc=notchDesc;

                for (int j = 0; j < nodeData.methods.size(); j++) {
                    System.out.println( nodeData.methods.get(j).name+":"+mn.name+"-"+ nodeData.methods.get(j).desc+":"+mn.desc);
                    if (nodeData.methods.get(j).name.equals(mn.name)
                            && nodeData.methods.get(j).desc.equals(mn.desc)) {
                        MethodNode oldMn = nodeData.methods.get(j);
                        System.out.println("Spliced in METHOD: " + targetClassName + "." + mn.name);
                        nodeData.methods.set(j, mn);
                        if (nodeData.superName != null && nodeData.name.equals(nodeSplice.superName)) {
                            ListIterator<AbstractInsnNode> nodeListIterator = mn.instructions.iterator();
                            while (nodeListIterator.hasNext()) {
                                AbstractInsnNode node = nodeListIterator.next();
                                if (node instanceof MethodInsnNode
                                        && node.getOpcode() == Opcodes.INVOKESPECIAL) {
                                    MethodInsnNode methodNode = (MethodInsnNode) node;
                                    if (targetClassName2.equals(methodNode.owner)) {
                                        methodNode.owner = nodeData.superName;
                                    }
                                }
                            }
                        }

                        added = true;
                        break;
                    }
                }

                if (!added) {
                    System.out.println("Added METHOD: " + targetClassName + "." + mn.name);
                    nodeData.methods.add(mn);
                    added = true;
                }
            }
        }

        for (int i = 0; i < nodeSplice.fields.size(); i++) {
            if (methodSet.contains(nodeSplice.fields.get(i).name)) {
                FieldNode mn = nodeSplice.fields.get(i);
                boolean added = false;

                for (int j = 0; j < nodeData.fields.size(); j++) {
                    if (nodeData.fields.get(j).name.equals(mn.name)
                            && nodeData.fields.get(j).desc.equals(mn.desc)) {
                        System.out.println("Spliced in FIELD: " + targetClassName + "." + mn.name);
                        nodeData.fields.set(j, mn);
                        added = true;
                        break;
                    }
                }

                if (!added) {
                    System.out.println("Added FIELD: " + targetClassName + "." + mn.name);
                    nodeData.fields.add(mn);
                    added = true;
                }
            }
        }

        return nodeData;
    }

}
