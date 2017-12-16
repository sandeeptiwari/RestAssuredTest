# RestAssuredTest

If you want to run your test on the basis of specific group

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="1265_Suite" parallel="false" verbose="2">
    <test name="92" parallel="false" preserve-order="true">
        <method-selectors>
            <method-selector>
                <script language="beanshell">
                    <![CDATA[whatGroup = System.getProperty("groupToRun");
                (groups.containsKey(whatGroup) || testngMethod.getGroups().length ==0);
                ]]>
                </script>
            </method-selector>
        </method-selectors>
        <classes>
            <class name="com.rationaleemotions.stackoverflow.MyTest1"/>
            <class name="com.rationaleemotions.stackoverflow.MyTest2"/>
        </classes>
    </test>
</suite>

run maven command
# mvn clean test -DsuiteXmlFile=dynamic_groups.xml -DgroupToRun=group2
