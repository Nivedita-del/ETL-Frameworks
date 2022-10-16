import boto3
import sys
from awsglue.transforms import *
from awsglue.utils import getResolvedOptions
from pyspark.context import SparkContext
from awsglue.context import GlueContext
from awsglue.job import Job


args = getResolvedOptions(sys.argv, ['TempDir', 'JOB_NAME'])
sc = SparkContext()
glueContext = GlueContext(sc)
spark = glueContext.spark_session
job = Job(glueContext)
job.init(args['JOB_NAME'], args)
awsRegion = '<<ADD YOUR AWS REGION HERE>>'
databaseName = '<<ADD YOUR DATABASE NAME HERE>>'
client = boto3.client('glue', region_name=awsRegion)

responseGetTables = client.get_tables(DatabaseName=databaseName)
tableList = responseGetTables['TableList']
for tableDict in tableList:
    tableName = tableDict['Name']
    shortName = tableName.split("_")
    redshiftSchema = '<<ADD YOUR REDSHIFT SCHEMA NAME HERE>>.' + shortName[2]
    preActionsCommand = 'TRUNCATE TABLE ' + redshiftSchema

    connection_options = {}
    connection_options['dbtable'] = redshiftSchema
    connection_options['database'] = '<<ADD YOUR DATABASE TABLE NAME HERE>>'
    connection_options['preactions'] = preActionsCommand

    dynamic_frame1 = glueContext.create_dynamic_frame.from_catalog(database=databaseName, table_name=tableName)
    if dynamic_frame1.count() == 0:
        print('\n-- Empty Table -- ' + tableName)
    else:
        print('\n-- COMPLETED STAGE 1')
        dynamic_frame1 = DropNullFields.apply(frame=dynamic_frame1)
        print('\n-- COMPLETED STAGE 2')
        dynamic_frame1 = glueContext.write_dynamic_frame.from_jdbc_conf(dynamic_frame1,
                                                                        "<<ADD YOUR REDSHIFT DATABASE NAME HERE>>",
                                                                        connection_options, args["TempDir"])
        print('\n-- COMPLETED STAGE 3')

job.commit()
