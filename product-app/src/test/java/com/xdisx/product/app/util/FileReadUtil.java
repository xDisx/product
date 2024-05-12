package com.xdisx.product.app.util;

import lombok.experimental.UtilityClass;
import org.springframework.core.io.ClassPathResource;
import software.amazon.awssdk.utils.IoUtils;

import java.io.IOException;

@UtilityClass
public class FileReadUtil {
    public String readResourceAsString(String filePath) {
        try {
            var expectedResult = IoUtils.toUtf8String(new ClassPathResource(filePath).getInputStream());
            expectedResult = expectedResult.replace("\r\n", "");
            return expectedResult;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
