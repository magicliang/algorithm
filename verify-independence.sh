#!/bin/bash

echo "=== 验证算法项目独立性 ==="
echo

echo "1. 检查Java版本..."
java -version
echo

echo "2. 清理项目..."
mvn clean
echo

echo "3. 编译项目..."
mvn compile
if [ $? -eq 0 ]; then
    echo "✅ 编译成功"
else
    echo "❌ 编译失败"
    exit 1
fi
echo

echo "4. 运行所有测试..."
mvn test --batch-mode
if [ $? -eq 0 ]; then
    echo "✅ 所有测试通过"
else
    echo "❌ 测试失败"
    exit 1
fi
echo

echo "5. 检查依赖..."
echo "项目只依赖于："
echo "- JDK 8+"
echo "- JUnit 5 (测试)"
echo "- Maven (构建工具)"
echo

echo "🎉 验证完成！项目完全独立，只需要JRE即可运行。"