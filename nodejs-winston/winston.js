const winston = require('winston');
const winstonDaily = require('winston-daily-rotate-file');
const path = require('path');

const {combine, timestamp, printf, simple} = winston.format;

const logDirectoryPath = path.join(__dirname, 'logs');

const logger = winston.createLogger({
    level: 'silly',
    format: combine(
        timestamp({format: 'YYYY-MM-DD HH:mm:ss'}),
        printf(({timestamp, level, message}) => {
            return `${timestamp} [${level}]: ${level}`
        }),
    ),    
    defaultMeta: {service: 'Test-Service'},
    transports: [
        new winstonDaily({
            level: 'info',
            datePattern: 'YYYY-MM-DD',
            dirname: logDirectoryPath,
            filename: `%DATE%.log`,
            maxFiles: 30,
            zippedArchive: true, 
        }),
        new winstonDaily({
            level: 'error',
            datePattern: 'YYYY-MM-DD',
            dirname: logDirectoryPath + '/error',
            filename: `%DATE%.error.log`,
            maxFiles: 30,
            zippedArchive: true,
          }),
    ],
});

if (process.env.NODE_ENV !== 'production') {
    logger.add(new winston.transports.Console({
        // format: simple(),
        format: winston.format.combine(
            winston.format.colorize(), // log level별로 색상 적용하기
            winston.format.simple(), // `${info.level}: ${info.message} JSON.stringify({ ...rest })` 포맷으로 출력
         ),
    }));
};

module.exports = { logger };