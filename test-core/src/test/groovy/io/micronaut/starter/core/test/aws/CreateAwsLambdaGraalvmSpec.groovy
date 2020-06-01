package io.micronaut.starter.core.test.aws

import io.micronaut.starter.application.ApplicationType
import io.micronaut.starter.test.BuildToolCombinations
import io.micronaut.starter.test.CommandSpec
import io.micronaut.starter.options.BuildTool
import io.micronaut.starter.options.Language
import io.micronaut.starter.options.TestFramework
import spock.lang.Requires
import spock.lang.Unroll

class CreateAwsLambdaGraalvmSpec extends CommandSpec {

    @Requires({ jvm.isJava8() || jvm.isJava11() })
    @Unroll
    void 'create-#applicationType with features aws-lambda, graalvm and lang #lang and build #build and test framework: #testFramework'(ApplicationType applicationType, Language lang, BuildTool build, TestFramework testFramework

    ) {
        given:
        List<String> features = ['aws-lambda', 'graalvm']
        generateProject(lang, build, features, applicationType, testFramework)

        when:
        String output = executeBuild(build, "test")

        then:
        output.contains("BUILD SUCCESS")

        where:
        [applicationType, lang, build, testFramework] << [[ApplicationType.DEFAULT, ApplicationType.FUNCTION], Language.values() - [Language.GROOVY], BuildToolCombinations.buildTools, TestFramework.values()].combinations()

    }


    @Override
    String getTempDirectoryPrefix() {
        "test-awslambdagraalvm"
    }
}
