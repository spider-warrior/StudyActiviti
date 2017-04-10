package com.jy.activiti.helper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AbstractTypeHierarchyTraversingFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.util.Set;
import java.util.TreeSet;

public class ScannerHelper {

    protected static final Log logger = LogFactory.getLog(ScannerHelper.class);

    public static void main(String[] args) {
        ScannerHelper scannerHelper = new ScannerHelper();
        scannerHelper.setPackagesToScan("com.wxsk.data.collector");
        Set<String> classes = scannerHelper.scan();
        for (String clazz: classes) {
            System.out.println(clazz);
        }
    }

    private static final String RESOURCE_PATTERN = "/**/*.class";

    private static final String PACKAGE_INFO_SUFFIX = ".package-info";

    private TypeFilter[] entityTypeFilters = { new DataCollectorFilter(Scope.class, false)};

    private String[] packagesToScan;
    private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    public void setPackagesToScan(String... packagesToScan) {
        this.packagesToScan = packagesToScan;
    }

    public Set<String> scan() {
        Set<String> entityClassNames = new TreeSet<String>();
        Set<String> packageNames = new TreeSet<String>();
        try {
            for (String pkg : packagesToScan) {
                String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                ClassUtils.convertClassNameToResourcePath(pkg) + RESOURCE_PATTERN;
                Resource[] resources = resourcePatternResolver.getResources(pattern);
                MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);
                for (Resource resource : resources) {
                    if (resource.isReadable()) {
                        MetadataReader reader = readerFactory.getMetadataReader(resource);
                        String className = reader.getClassMetadata().getClassName();
                        if (matchesEntityTypeFilter(reader, readerFactory)) {
                            entityClassNames.add(className);
                        }
                        else if (className.endsWith(PACKAGE_INFO_SUFFIX)) {
                            packageNames.add(className.substring(0, className.length() - PACKAGE_INFO_SUFFIX.length()));
                        }
                    }
                }
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Failed to scan classpath for unlisted classes", ex);
        }
        return entityClassNames;
    }

    private boolean matchesEntityTypeFilter(MetadataReader reader, MetadataReaderFactory readerFactory) throws IOException {
        if (entityTypeFilters != null) {
            for (TypeFilter filter : entityTypeFilters) {
                if (filter.match(reader, readerFactory)) {
                    return true;
                }
            }
        }
        return false;
    }
}

class DataCollectorFilter extends AbstractTypeHierarchyTraversingFilter {

    private final Class<? extends Annotation> annotationType;

    private final boolean considerMetaAnnotations;

    public DataCollectorFilter(Class<? extends Annotation> annotationType, boolean considerMetaAnnotations, boolean considerInterfaces) {
        super(annotationType.isAnnotationPresent(Inherited.class), considerInterfaces);
        this.annotationType = annotationType;
        this.considerMetaAnnotations = considerMetaAnnotations;
    }

    public DataCollectorFilter(Class<? extends Annotation> annotationType, boolean considerMetaAnnotations) {
        this(annotationType, considerMetaAnnotations, false);
    }

    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        return super.match(metadataReader, metadataReaderFactory);
    }

    @Override
    protected boolean matchSelf(MetadataReader metadataReader) {
        AnnotationMetadata metadata = metadataReader.getAnnotationMetadata();
        return metadata.hasAnnotation(annotationType.getName()) ||
                considerMetaAnnotations && metadata.hasMetaAnnotation(annotationType.getName());
    }

    @Override
    protected Boolean matchSuperClass(String superClassName) {
        return hasAnnotation(superClassName);
    }

    @Override
    protected Boolean matchInterface(String interfaceName) {
        return hasAnnotation(interfaceName);
    }
    protected Boolean hasAnnotation(String typeName) {
        if (Object.class.getName().equals(typeName)) {
            return false;
        }
        else if (typeName.startsWith("java")) {
            try {
                Class<?> clazz = ClassUtils.forName(typeName, getClass().getClassLoader());
                return (considerMetaAnnotations ? AnnotationUtils.getAnnotation(clazz, annotationType) :
                        clazz.getAnnotation(annotationType)) != null;
            }
            catch (Throwable ex) {
                logger.error("", ex);
            }
        }
        return null;
    }
}
